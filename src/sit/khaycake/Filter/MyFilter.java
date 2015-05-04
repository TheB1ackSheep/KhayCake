package sit.khaycake.Filter;

import javax.servlet.Filter;

/**
 * Created by Falook Glico on 4/26/2015.
 */
public abstract class MyFilter implements Filter {

    public static FormAttribute attribute(String name) {
        return new FormAttribute(name, null, true, -1, -1, false, false);
    }

    public static FormAttribute attribute(String name, int min) {
        return new FormAttribute(name, null, true, min, -1, false, false);
    }

    public static FormAttribute attribute(String name, int min, int max) {
        return new FormAttribute(name, null, true, min, max, false, false);
    }

    public static FormAttribute integerAttribute(String name) {
        return new FormAttribute(name, null, true, -1, -1, true, false);
    }

    public static FormAttribute integerAttribute(String name, int min) {
        return new FormAttribute(name, null, true, min, -1, true, false);
    }

    public static FormAttribute integerAttribute(String name, int min, int max) {
        return new FormAttribute(name, null, true, min, max, true, false);
    }

    public static FormAttribute floatAttribute(String name) {
        return new FormAttribute(name, null, true, -1, -1, false, true);
    }

    public static FormAttribute floatAttribute(String name, int min) {
        return new FormAttribute(name, null, true, min, -1, false, true);
    }

    public static FormAttribute floatAttribute(String name, int min, int max) {
        return new FormAttribute(name, null, true, min, max, false, true);
    }

    public static FormAttribute attribute(String name, String nickname) {
        return new FormAttribute(name, nickname, true, -1, -1, false, false);
    }

    public static FormAttribute attribute(String name, String nickname, int min) {
        return new FormAttribute(name, nickname, true, min, -1, false, false);
    }

    public static FormAttribute attribute(String name, String nickname, int min, int max) {
        return new FormAttribute(name, nickname, true, min, max, false, false);
    }

    public static FormAttribute integerAttribute(String name, String nickname) {
        return new FormAttribute(name, nickname, true, -1, -1, true, false);
    }

    public static FormAttribute integerAttribute(String name, String nickname, int min) {
        return new FormAttribute(name, nickname, true, min, -1, true, false);
    }

    public static FormAttribute integerAttribute(String name, String nickname, int min, int max) {
        return new FormAttribute(name, nickname, true, min, max, true, false);
    }

    public static FormAttribute floatAttribute(String name, String nickname) {
        return new FormAttribute(name, nickname, true, -1, -1, false, true);
    }

    public static FormAttribute floatAttribute(String name, String nickname, int min) {
        return new FormAttribute(name, nickname, true, min, -1, false, true);
    }

    public static FormAttribute floatAttribute(String name, String nickname, int min, int max) {
        return new FormAttribute(name, nickname, true, min, max, false, true);
    }

    public static FormAttribute fileAttribute(String name, String nickname) {
        return new FormAttribute(name, nickname, true);
    }

    public static FormAttribute integerAttribute(String name, String nickname, boolean required) {
        return new FormAttribute(name, nickname, required, -1, -1, true, false);
    }

    public static FormAttribute floatAttribute(String name, String nickname, boolean required) {
        return new FormAttribute(name, nickname, required, -1, -1, false, true);
    }

}
