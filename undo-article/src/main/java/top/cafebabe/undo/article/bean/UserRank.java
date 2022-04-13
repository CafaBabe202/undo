package top.cafebabe.undo.article.bean;

import lombok.Data;

/**
 * @author cafababe
 */
@Data
public class UserRank {
    private int UserId;
    private long number;
    private long visit;
    private long like;
}
