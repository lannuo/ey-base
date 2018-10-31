package cn.sky999.editor;

import java.beans.PropertyEditorSupport;
import java.text.ParseException;
import java.util.Date;

import com.github.pagehelper.util.StringUtil;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;
public class MultipleDateEditor extends PropertyEditorSupport {
	public static final String DEFAULT_OUTPUT_FORMAT = "dd/mm/yyyy";
	public static final String[] DEFAULT_INPUT_FORMATS = { "dd/mm/yyyy hh:mm:ss", "dd-mm-yyyy hh:mm:ss",
			"dd/mm/yy hh:mm:ss", "dd-mm-yy hh:mm:ss", "dd/mm/yyyy", "dd-mm-yyyy", "dd/mm/yy", "dd-mm-yy","yyyy-mm-dd" };
	private String outputFormat;
	private String[] inputFormats;
	private boolean allowEmpty;

	public MultipleDateEditor() {
		this.outputFormat = "dd/mm/yyyy";
		this.inputFormats = DEFAULT_INPUT_FORMATS;
		this.allowEmpty = false;
	}

	public MultipleDateEditor(String outputFormat, String[] inputFormats) {
		this.outputFormat = outputFormat;
		this.inputFormats = inputFormats;
		this.allowEmpty = false;
	}

	public MultipleDateEditor(String outputFormat, String[] inputFormats, boolean allowEmpty) {
		this.outputFormat = outputFormat;
		this.inputFormats = inputFormats;
		this.allowEmpty = allowEmpty;
	}

	public String getAsText() {
		if ((this.allowEmpty) && (getValue() == null)) {
			return "";
		}

		return DateFormatUtils.format((Date) getValue(), this.outputFormat);
	}

	public void setAsText(String text) throws IllegalArgumentException {
		try {
			if (!StringUtil.isEmpty(text)) {
				setValue(DateUtils.parseDate(text, this.inputFormats));
				return;
			}
			if (this.allowEmpty) {
				setValue(null);
				return;
			}
			throw new IllegalArgumentException("The text specified for parsing is null");
		} catch (ParseException ex) {
			throw new IllegalArgumentException(
					"Could not parse text [" + text + "] into any available date input formats", ex);
		}
	}

	public boolean isAllowEmpty() {
		return this.allowEmpty;
	}

	public void setAllowEmpty(boolean allowEmpty) {
		this.allowEmpty = allowEmpty;
	}

	public String[] getInputFormats() {
		return this.inputFormats;
	}

	public void setInputFormats(String[] inputFormats) {
		this.inputFormats = inputFormats;
	}

	public String getOutputFormat() {
		return this.outputFormat;
	}

	public void setOutputFormat(String outputFormat) {
		this.outputFormat = outputFormat;
	}
}
