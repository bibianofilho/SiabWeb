package br.com.siab.biblioteca.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * V.O. Generico
 * @version 1.0
 * @author Manoel Bibiano ( bibianofilho@gmail.com )
 *
 */
public class BBValueObject implements Serializable {

	private ArrayList lines;

	private HashMap columns;

	private boolean isDirty;

	public BBValueObject() {

		lines = new ArrayList();
		columns = new HashMap();
	}

	public BBValueObject(int size) {

		lines = new ArrayList(size);
		columns = new HashMap();
	}

	public BBValueObject(ResultSet rs) throws SQLException {
		this(rs, false);
	}

	public BBValueObject(ResultSet rs, boolean compModel) throws SQLException {
		this();
		ResultSetMetaData rsmd = rs.getMetaData();
		Object o = null;
		for (int line = 0; rs.next(); line++) {
			for (int i = 1; i <= rsmd.getColumnCount(); i++) {
				String columnName = rsmd.getColumnName(i);
				o = rs.getObject(columnName);
				if ((o instanceof BigDecimal) && rsmd.getScale(i) == 0) {
					BigDecimal bd = (BigDecimal) o;
					long l = bd.longValue();
					if (l > 0x7fffffffL) {
						o = new Long(l);
					} else {
						o = new Integer(bd.intValue());
					}
				}
				if (compModel) {
					if (o instanceof Double) {
						o = new BigDecimal(((Double) o).doubleValue());
					} else if (o instanceof Float) {
						o = new BigDecimal(((Float) o).doubleValue());
					} else if (o instanceof Short) {
						o = new Integer(((Short) o).toString());
					}
				}
				set(line, columnName, o);
			}

		}

	}

	public int size() {
		return lines.size();
	}

	public int getColumnNumber(String columnName) {
		Integer i = (Integer) columns.get(columnName);
		if (i == null) {
			return -1;
		} else {
			return i.intValue();
		}
	}

	public void set(int line, String columnName, Object o) {
		columnName = columnName.toLowerCase().trim();
		Integer columnNumber = (Integer) columns.get(columnName);
		if (columnNumber == null) {
			columnNumber = new Integer(columns.size());
			columns.put(columnName, columnNumber);
		}
		if (line > lines.size() - 1) {
			for (int j = lines.size(); line >= j; j++) {
				lines.add(new HashMap());
			}

		}
		HashMap lineInformation = (HashMap) lines.get(line);
		lineInformation.put(columnNumber, o);
		lines.set(line, lineInformation);
		isDirty = true;
	}

	public void add(String columnNames[], Object objects[]) throws Exception {
		if (columnNames.length != objects.length) {
			throw new Exception(new Error(
					"sfc.error.valueObject.invalidOperation"));
		}
		int lastLine = size();
		for (int i = 0; i < objects.length; i++) {
			set(lastLine, columnNames[i], objects[i]);
		}

	}

	public void add(BBValueObject vo) {
		if (vo == null) {
			throw new NullPointerException("Parametro nao pode ser null.");
		}
		int countLines = vo.size();
		if (countLines > 0) {
			String cols[] = vo.getColumnNames();
			Object values[] = new Object[cols.length];
			for (int l = 0; l < countLines; l++) {
				for (int c = values.length - 1; c >= 0; c--) {
					values[c] = vo.get(l, cols[c]);
				}

				try {
					add(cols, values);
				} catch (Exception e) {
				}
			}

		}
	}

	public Object get(int line, String columnName) {
		Object o = null;
		columnName = columnName.toLowerCase();
		Integer columnNumber = (Integer) columns.get(columnName);
		if (columnNumber == null) {
			return null;
		}
		HashMap lineInformation = (HashMap) lines.get(line);
		if (lineInformation == null) {
			return null;
		} else {
			o = lineInformation.get(columnNumber);
			return o;
		}
	}

	public double getDouble(int line, String columnName) {
		Object o = get(line, columnName);
		if (o == null) {
			return 0.0D;
		}
		if (o instanceof Integer) {
			return ((Integer) o).doubleValue();
		}
		if (o instanceof Long) {
			return ((Long) o).doubleValue();
		}
		if (o instanceof Float) {
			return ((Float) o).doubleValue();
		}
		if (o instanceof Double) {
			return ((Double) o).doubleValue();
		}
		if (o instanceof Short) {
			return ((Short) o).doubleValue();
		}
		if (o instanceof Byte) {
			return ((Byte) o).doubleValue();
		}
		if (o instanceof BigDecimal) {
			return ((BigDecimal) o).doubleValue();
		}
		if (o instanceof String) {
			return (new Double((String) o)).doubleValue();
		} else {
			throw new ClassCastException();
		}
	}

	public int getInteger(int line, String columnName) {
		Object o = get(line, columnName);
		if (o == null) {
			return 0;
		}
		if (o instanceof Integer) {
			return ((Integer) o).intValue();
		}
		if (o instanceof Long) {
			return ((Long) o).intValue();
		}
		if (o instanceof Float) {
			return ((Float) o).intValue();
		}
		if (o instanceof Double) {
			return ((Double) o).intValue();
		}
		if (o instanceof Short) {
			return ((Short) o).intValue();
		}
		if (o instanceof Byte) {
			return ((Byte) o).intValue();
		}
		if (o instanceof BigDecimal) {
			return ((BigDecimal) o).intValue();
		}
		if (o instanceof String) {
			return (new Double((String) o)).intValue();
		} else {
			throw new ClassCastException();
		}
	}

	public long getLong(int line, String columnName) {
		Object o = get(line, columnName);
		if (o == null) {
			return 0L;
		}
		if (o instanceof Integer) {
			return ((Integer) o).longValue();
		}
		if (o instanceof Long) {
			return ((Long) o).longValue();
		}
		if (o instanceof Float) {
			return ((Float) o).longValue();
		}
		if (o instanceof Double) {
			return ((Double) o).longValue();
		}
		if (o instanceof Short) {
			return ((Short) o).longValue();
		}
		if (o instanceof Byte) {
			return ((Byte) o).longValue();
		}
		if (o instanceof BigDecimal) {
			return ((BigDecimal) o).longValue();
		}
		if (o instanceof String) {
			return (new Double((String) o)).longValue();
		} else {
			throw new ClassCastException();
		}
	}

	public BigDecimal getBigDecimal(int line, String columnName) {
		Object o = get(line, columnName);
		if (o == null) {
			return new BigDecimal(0.0D);
		}
		if (o instanceof BigDecimal) {
			return (BigDecimal) o;
		}
		if (o instanceof Integer) {
			return new BigDecimal(((Integer) o).doubleValue());
		}
		if (o instanceof Long) {
			return new BigDecimal(((Long) o).doubleValue());
		}
		if (o instanceof Float) {
			return new BigDecimal(((Float) o).doubleValue());
		}
		if (o instanceof Double) {
			return new BigDecimal(((Double) o).doubleValue());
		}
		if (o instanceof Short) {
			return new BigDecimal(((Short) o).doubleValue());
		}
		if (o instanceof Byte) {
			return new BigDecimal(((Byte) o).doubleValue());
		}
		if (o instanceof String) {
			return new BigDecimal((String) o);
		} else {
			throw new ClassCastException();
		}
	}

	public Date getUtilDate(int line, String columnName) {
		return (Date) get(line, columnName);
	}

	public java.sql.Date getDate(int line, String columnName) {
		Object o = get(line, columnName);
		if (o instanceof Timestamp) {
			return new java.sql.Date(((Timestamp) o).getTime());
		} else {
			return (java.sql.Date) o;
		}
	}

	public String getString(int line, String columnName) {
		return (String) get(line, columnName);
	}

	public boolean getBoolean(int line, String columnName) {
		Boolean b = (Boolean) get(line, columnName);
		if (b == null) {
			return false;
		} else {
			return b.booleanValue();
		}
	}

	public void remove(int line) {
		lines.remove(line);
	}

	public void clear() {
		isDirty = false;
		lines.clear();
		columns.clear();
	}

	public String[] getColumnNames() {
		String columnNames[] = new String[columns.size()];
		Iterator iterator = columns.keySet().iterator();
		for (int i = 0; iterator.hasNext(); i++) {
			columnNames[i] = (String) iterator.next();
		}

		return columnNames;
	}

	public String toString() {
		StringBuffer sb = new StringBuffer();
		String columns[] = getColumnNames();
		sb.append("line \t");
		for (int j = 0; j < columns.length; j++) {
			sb.append(columns[j]);
			sb.append("\t");
		}

		sb.append("\n");
		for (int i = 0; i < size(); i++) {
			sb.append(i + "\t");
			for (int j = 0; j < columns.length; j++) {
				try {
					sb.append(get(i, columns[j]));
				} catch (Exception e) {
				}
				sb.append("\t");
			}

			sb.append("\n");
		}

		return sb.toString();
	}

	public byte[] toByteArray() throws Exception {
		ByteArrayOutputStream baos;
		baos = new ByteArrayOutputStream();
		ObjectOutputStream oos = new ObjectOutputStream(baos);
		oos.writeObject(this);
		return baos.toByteArray();

	}

	public String toBinary() throws Exception {
		StringBuffer b = new StringBuffer();
		byte data[] = toByteArray();
		for (int i = data.length - 1; i >= 0; i--) {
			b.append(data[i]);
			b.append(";");
		}

		return b.toString();
	}

	public static BBValueObject fromBinary(String data) throws IOException,
			ClassNotFoundException {
		byte a[];
		String dadosLidos[] = data.split(";");
		a = new byte[dadosLidos.length];
		int j = dadosLidos.length - 1;
		for (int i = 0; i < a.length; i++) {
			a[j] = Byte.valueOf(dadosLidos[i]).byteValue();
			j--;
		}

		BBValueObject vo;
		ByteArrayInputStream bais = new ByteArrayInputStream(a);
		ObjectInputStream ois = new ObjectInputStream(bais);
		vo = (BBValueObject) ois.readObject();
		return vo;
	}

	public boolean equals(Object o) {
		if (!(o instanceof BBValueObject)) {
			return false;
		}
		BBValueObject vo = (BBValueObject) o;
		if (vo.size() != size()) {
			return false;
		}
		String columnNames[] = getColumnNames();
		String columnNamesOriginal[] = vo.getColumnNames();
		for (int i = 0; i < columnNames.length; i++) {
			if (!columnNames[i].equals(columnNamesOriginal[i])) {
				return false;
			}
			for (int j = 0; j < vo.size(); j++) {
				Object o1 = vo.get(j, columnNames[i]);
				Object o2 = get(j, columnNames[i]);
				if (o1 instanceof BigDecimal) {
					if (!(o2 instanceof BigDecimal)) {
						return false;
					}
					if (((BigDecimal) o1).compareTo((BigDecimal) o2) != 0) {
						return false;
					}
				} else if (!o1.equals(o2)) {
					return false;
				}
			}

		}

		return true;
	}

	public BBValueObject subVo(int beginLine, int endLine) {
		BBValueObject vo = new BBValueObject();
		int sizeVo = size();
		int indexVo = 0;
		String cols[] = getColumnNames();
		int colsSize = cols.length;
		for (int row = beginLine; row < endLine && row < sizeVo; row++) {
			for (int col = 0; col < colsSize; col++) {
				vo.set(indexVo, cols[col], get(row, cols[col]));
			}

			indexVo++;
		}

		return vo;
	}

	public boolean isEmpty(int line, String columnName) {
		Object o = get(line, columnName);
		return o == null || o.toString().trim().length() == 0;
	}

	public boolean isAnyEmpty(String columnName) {
		int tam = size();
		for (int i = 0; i < tam; i++) {
			if (isEmpty(i, columnName)) {
				return true;
			}
		}

		return false;
	}

	public List getList() {
		return lines;
	}

	public void set(String columnName, Object value) {
		Integer colNumber = (Integer) columns.get(columnName);
		if (colNumber == null) {
			colNumber = new Integer(columns.size());
			columns.put(columnName, colNumber);
		}
		int size = lines.size();
		for (int index = 0; index < size; index++) {
			Map line = (HashMap) lines.get(index);
			line.put(colNumber, value);
		}

	}

	public void set(String columnName, Object value[]) {
		Integer colNumber = (Integer) columns.get(columnName);
		if (colNumber == null) {
			colNumber = new Integer(columns.size());
			columns.put(columnName, colNumber);
		}
		int size = lines.size();
		for (int index = 0; index < size; index++) {
			Map line = (HashMap) lines.get(index);
			line.put(colNumber, value[index]);
		}

	}
}
