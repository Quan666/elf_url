package club.myelf.entity;

import java.io.Serializable;
import lombok.Data;
import java.util.Date;
import java.util.List;

/**
 * Url
 * @author quan666 2020-06-21
 */
@Data
public class Url implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * id  ID
     */
    private Long id;

    /**
     * url  链接
     */
    private String url;

    /**
     * short_url  短链
     */
    private String shortUrl;

    /**
     * MD5  MD5
     */
    private String MD5;
}