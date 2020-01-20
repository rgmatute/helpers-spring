package com.github.rgmatute;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * Source code: https://github.com/rgmatute
 * 
 * @author Ronny Matute
 **/
@SuppressWarnings({ "unused" })
public class EasyDateTime {
	public EasyDateTime(){
		this.resetValues();
	}
	
	public EasyDateTime(String dateString, String formattDateSend){
		//
	}
	
	public String fromNow() {
		return "falta logica";
	}

	public String extractDate(Date date, String formatter) {
		SimpleDateFormat df = new SimpleDateFormat(formatter);
		return df.format(date);
	}

	public int getYear(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return calendar.get(Calendar.YEAR);
	}

	public int getMonth(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return calendar.get(Calendar.MONTH) + 1;
	}

	public int getDay(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return calendar.get(Calendar.DAY_OF_MONTH);
	}

	public int getDaysOfDifference(Date fecha1, Date fecha2) {
		int dias = (int) ((fecha1.getTime() - fecha2.getTime()) / 86400000);
		return dias;
	}

	public Date stringToDate(String stringDateTime) {
		return stringToDate(stringDateTime, "dd/MM/yyyy HH:mm:ss");
	}

	public Date stringToDate(String stringDate, String formatt) {
		Calendar calendar = Calendar.getInstance();
		try {
			SimpleDateFormat formato = new SimpleDateFormat(formatt);
			calendar.setTime(formato.parse(stringDate));
		} catch (Exception e) {
			System.out.println(e);
		}
		return new Date(calendar.getTime().getTime());
	}

	public String currentDate(String formatt) {
		DateTimeFormatter format = DateTimeFormatter.ofPattern(formatt);
		LocalDateTime localDateTime = LocalDateTime.now();
		return (format.format(localDateTime));
	}

	public String currentStringDate() {
		return currentDate("dd/MM/yyyy");
	}
	
	public Date currentDate() {
		return stringToDate(currentDate("dd/MM/yyyy HH:mm:ss"));
	}
	
	public String currentDate(Date date, String formatt) {
		DateFormat df = new SimpleDateFormat(formatt);
		return df.format(date);
	}

	public String currentDateTimeIso() {
		DateTimeFormatter format = DateTimeFormatter.ISO_DATE_TIME;
		LocalDateTime localDateTime = LocalDateTime.now();
		return (format.format(localDateTime) + "Z");
	}

	public String currentDateTime() {
		return currentDate("dd/MM/yyyy HH:mm:ss");
	}

	public boolean isDateTime(String stringDateTime) {
		return isMatch(stringDateTime,
				"(0[1-9]|1[0-9]|2[0-9]|3[01])(/?-?)(0[1-9]|1[012])(/?-?)([1-9][0-9]{3}) (0[1-9]|1[0-9]|2[0-4])(:[0-5][0-9])(:[0-5][0-9])");
	}

	public String currentTime() {
		return currentDate("HH:mm:ss");
	}

	/**
	 * Acepta cualquier tipo de Expresion regular
	 */
	public boolean isMatch(String value, String PatternRegEx) {
		Pattern pattern = Pattern.compile(PatternRegEx);
		Matcher matcher = pattern.matcher(value);
		return matcher.matches();
		// return value.matches(PatternRegEx);
	}

	/* ====================================================================== */

	public String format() {
		return currentDateTimeIso();
	}
	
	private Date currentDate(int type, int value) {
		Date date = currentDate();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(type, value);
		return calendar.getTime();
	}
	
	public String calendar() {
		String r = null;
		switch(type) {
		case "days":
			if(this.value == 1) {
				r = currentDate("'Mañana a las' HH:mm a");
			}else if(this.value == -1) {
				r = currentDate("'Ayer a las' HH:mm a");
			}else if(this.value < -6 || this.value > 6) {
				return currentDate(currentDate(Calendar.DAY_OF_YEAR,this.value), "MM/dd/yyyy");
			}else if(this.value < 0) {
				return currentDate(currentDate(Calendar.DAY_OF_YEAR,this.value), "EEEE 'pasado a las' HH:mm:ss a");
			}else if(this.value > 0) {
				return currentDate(currentDate(Calendar.DAY_OF_YEAR,this.value), "EEEE 'a las' HH:mm:ss a");
			}
			break;
			default:
				r = currentDate("'Hoy a las' HH:mm a");
				break;
		
		}
		this.resetValues();
		return r;
	}
	
	private int add ;
	private int value;
	private String type;
	private String fn;
	
	private void resetValues() {
		this.add = 0;
		this.type = "";
		this.fn = "null";
	}

	public EasyDateTime add(int value, String type) {
		this.value = value;
		this.type = type;
		this.fn = "add";
		return this;
	}

	public EasyDateTime subtract(int value, String type) {
		this.value = -value;
		this.type = type;
		this.fn = "subtract";
		return this;
	}

	public String format(String regex) {
		regex = regex.replace("o", "\'th'");
		regex = regex.replace("dddd", "EEEE");
		return currentDate(regex = regex.replace("[", "\'").replace("]", "\'"));
	}
	
	public int numeroDiasEntreDosFechas(Date fecha1, Date fecha2){
	     long startTime = fecha1.getTime();
	     long endTime = fecha2.getTime();
	     long diffTime = endTime - startTime;
	     return (int)TimeUnit.DAYS.convert(diffTime, TimeUnit.MILLISECONDS);
	}
	
	public RelativeTime startOf(String type) {
		// falta logica
		return new RelativeTime();
	}
	
	public RelativeTime endOf(String type) {
		// falta logica
		return new RelativeTime();
	}
	
	
	/**
	 * Método estático que añade o resta periodos de tiempo a una fecha dada 
	 * @url https://masqueprogramar.wordpress.com/2016/11/18/funcion-sumar-dias-a-una-fecha/	 
	 * @param fecha, fecha inicial   
	 * @param campo tipo de periodo (pe. Calendar.HOUR) 
	 * @param valor cantidad de tiempo a añadir o restar
	 * @return fecha modificada con el valor y campo especificados
	 */
	public Date variarFecha(Date fecha, int campo, int valor){
	      if (valor==0) return fecha;
	      Calendar calendar = Calendar.getInstance();
	      calendar.setTime(fecha); 
	      calendar.add(campo, valor); 
	      return calendar.getTime();
	}
	
	/**
	 * Método estático que devuelve el número de días que tiene el mes de una fecha 
	 * @param fecha
	 * @return número de días del mes correspondiente
	 */
	public int numeroDiasMes(Date fecha){
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(fecha);	
		return calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
	}
	
	// Relative Time
	 public class RelativeTime {
			public String fromNow() {
				// falta logica
				return "falta logica";
			}
	 }

}
