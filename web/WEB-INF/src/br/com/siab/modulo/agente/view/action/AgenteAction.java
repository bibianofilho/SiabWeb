package br.com.siab.modulo.agente.view.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import br.com.siab.biblioteca.struts.action.BaseAction;
import br.com.siab.modulo.agente.Agente;
import br.com.siab.modulo.agente.AgenteImpl;
import br.com.siab.modulo.agente.model.AgenteVO;
import br.com.siab.modulo.agente.view.form.AgenteForm;

/**
 * @version 1.0
 * @author Manoel Bibiano ( bibianofilho@gmail.com )
 * 
 */
public class AgenteAction extends BaseAction {

	private final String FORWARD_LISTAR = "listar";

	private final String FORWARD_INCLUIR = "incluir";

	private final String FORWARD_EDITAR = "editar";

	public ActionForward preparaPesquisa(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		return mapping.getInputForward();
	}

	public ActionForward pesquisar(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		ActionForward forward = null;
		HttpSession session = request.getSession(true);
		AgenteForm agenteForm = (AgenteForm) form;
		AgenteVO agenteVO = new AgenteVO();

		popularVO(agenteVO, agenteForm);

		try {
			Agente agente = AgenteImpl.getInstance();
			List lAgentes = agente.pesquisar(agenteVO);
			session.setAttribute(Agente.LISTA_AGENTES, lAgentes);

			forward = mapping.findForward(FORWARD_LISTAR);

		} catch (Exception e) {
			saveErrors(e, request);
			forward = mapping.findForward("ERROR_PAGE");
		}

		return forward;
	}

	public ActionForward preparaInsercao(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		return mapping.findForward(FORWARD_INCLUIR);
	}

	public ActionForward inserir(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		ActionForward forward = null;
		AgenteForm agenteForm = (AgenteForm) form;
		AgenteVO agenteVO = new AgenteVO();
		ActionMessages messages = new ActionMessages();

		popularVO(agenteVO, agenteForm);

		try {
			Agente agente = AgenteImpl.getInstance();
			agente.inserir(agenteVO);

			messages
					.add("success", new ActionMessage("message.insert.success"));
			saveMessages(request, messages);

			agenteForm.reset(mapping, request);

			forward = mapping.getInputForward();

		} catch (Exception e) {
			saveErrors(e, request);
			forward = mapping.findForward("ERROR_PAGE");
		}

		return forward;
	}

	/**
	 * Chamada para a tela de edição de agente.
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward preparaEdicao(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		ActionForward forward = null;
		AgenteForm agenteForm = (AgenteForm) form;
		AgenteVO agenteVO = null;
		AgenteVO retAgente = null;
		String id = request.getParameter("id");

		try {
			Agente agente = AgenteImpl.getInstance();
			agenteVO = new AgenteVO();
			agenteVO.setCodigo(new Integer(id));
			retAgente = agente.getAgenteById(agenteVO);

			agenteForm.setCodigo(retAgente.getCodigo().toString());
			agenteForm.setNome(retAgente.getNome());
			agenteForm.setLogin(retAgente.getLogin());
			agenteForm.setSenha(retAgente.getSenha());
			agenteForm.setResenha(retAgente.getSenha());

			forward = mapping.findForward(FORWARD_EDITAR);

		} catch (Exception e) {
			saveErrors(e, request);
			forward = mapping.findForward("ERROR_PAGE");
		}

		return forward;
	}

	public ActionForward editar(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		ActionForward forward = null;
		AgenteForm agenteForm = (AgenteForm) form;
		AgenteVO agenteVO = new AgenteVO();
		ActionMessages messages = new ActionMessages();

		popularVO(agenteVO, agenteForm);

		try {
			Agente agente = AgenteImpl.getInstance();
			agente.editar(agenteVO);

			messages
					.add("success", new ActionMessage("message.update.success"));
			saveMessages(request, messages);

			agenteForm.reset(mapping, request);

			forward = mapping.getInputForward();

		} catch (Exception e) {
			saveErrors(e, request);
			//forward = mapping.findForward("ERROR_PAGE");
			forward = mapping.findForward(FORWARD_EDITAR);
		}

		return forward;
	}

	/**
	 * Método utilizado para gerar dados para o usuário do palm.
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward gerarDados(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		ActionForward forward = null;
		String id = request.getParameter("id");
		ActionMessages messages = new ActionMessages();
		AgenteForm agenteForm = (AgenteForm) form;
		String login = agenteForm.getLogin();

		try {
			Agente agente = AgenteImpl.getInstance();
			agente.gerarDados(new Integer(id));

			messages.add("success", new ActionMessage("message.crud.default",
					"A geração de dados para o usuário " + login
							+ " foi realizada com sucesso."));
			saveMessages(request,messages);
			
			agenteForm.reset(mapping, request);
			
			forward = mapping.getInputForward();

		} catch (Exception e) {
			saveErrors(e, request);
			//forward = mapping.findForward("ERROR_PAGE");
			forward = mapping.findForward(FORWARD_EDITAR);
		}

		return forward;
	}

	/**
	 * Método interno utilizado para popular o bean
	 * 
	 * @param agenteVO
	 * @param agenteForm
	 * @throws Exception
	 */
	private void popularVO(AgenteVO agenteVO, AgenteForm agenteForm)
			throws Exception {
		String codigo = agenteForm.getCodigo();

		if (codigo != null && !"".equals(codigo)) {
			agenteVO.setCodigo(new Integer(codigo));
		}

		agenteVO.setNome(agenteForm.getNome());
		agenteVO.setLogin(agenteForm.getLogin());
		agenteVO.setSenha(agenteForm.getSenha());
	}
}
