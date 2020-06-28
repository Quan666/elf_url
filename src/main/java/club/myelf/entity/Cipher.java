package club.myelf.entity;

import java.io.Serializable;
import lombok.Data;
import java.util.Date;
import java.util.List;

/**
 * Cipher
 * @author quan666 2020-06-22
 */
@Data
public class Cipher implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * id  ID
     */
    private Long id;

    /**
     * message  密语
     */
    private String message;

    /**
     * passwd  查看密码
     */
    private String passwd;

    /**
     * code  链接
     */
    private String code;
}