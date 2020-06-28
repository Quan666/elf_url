package club.myelf.entity;

import java.io.Serializable;
import lombok.Data;
import java.util.Date;
import java.util.List;

/**
 * Oneread
 * @author quan666 2020-06-22
 */
@Data
public class Oneread implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * id  
     */
    private Long id;

    /**
     * message  信息
     */
    private String message;

    /**
     * status  状态
     */
    private Integer status;

    /**
     * code  链接
     */
    private String code;
}