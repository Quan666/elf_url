package club.myelf.entity;

import java.io.Serializable;
import lombok.Data;
import java.util.Date;
import java.util.List;

/**
 * User
 * @author quan666 2020-06-22
 */
@Data
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * id  ID
     */
    private Long id;

    /**
     * name  用户名
     */
    private String name;

    /**
     * passwd  
     */
    private String passwd;

    /**
     * type  用户类型
     */
    private String type;

    /**
     * apikey  API KEY
     */
    private String apikey;
}