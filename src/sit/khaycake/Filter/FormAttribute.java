package sit.khaycake.Filter;

/**
 * Created by Falook Glico on 4/26/2015.
 */

public class FormAttribute {
    private String name;
    private String nickName;
    private boolean required;
    private int min;
    private int max;
    private boolean isInteger;
    private boolean isFloat;
    private boolean isFile;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isRequired() {
        return required;
    }

    public void setRequired(boolean required) {
        this.required = required;
    }

    public int getMin() {
        return min;
    }

    public void setMin(int min) {
        this.min = min;
    }

    public int getMax() {
        return max;
    }

    public void setMax(int max) {
        this.max = max;
    }

    public boolean isInteger() {
        return isInteger;
    }

    public void setInteger(boolean isInteger) {
        this.isInteger = isInteger;
    }

    public boolean isFloat() {
        return isFloat;
    }

    public void setFloat(boolean isFloat) {
        this.isFloat = isFloat;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public void setIsInteger(boolean isInteger) {
        this.isInteger = isInteger;
    }

    public void setIsFloat(boolean isFloat) {
        this.isFloat = isFloat;
    }

    public boolean isFile() {
        return isFile;
    }

    public void setIsFile(boolean isFile) {
        this.isFile = isFile;
    }

    public FormAttribute(String name,String nickName, boolean required, int min, int max, boolean isInteger, boolean isFloat) {
        this.name = name;
        this.nickName = nickName;
        this.required = required;
        this.min = min;
        this.max = max;
        this.isInteger = isInteger;
        this.isFloat = isFloat;
    }

    public FormAttribute(String name,String nickName, boolean isFile) {
        this.name = name;
        this.nickName = nickName;
        this.required = true;
        this.isInteger = false;
        this.isFloat = false;
        this.isFile = isFile;
    }

    @Override
    public String toString() {
        return (this.nickName != null)?nickName:name;
    }
}
