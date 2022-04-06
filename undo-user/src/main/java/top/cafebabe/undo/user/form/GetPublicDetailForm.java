package top.cafebabe.undo.user.form;

import lombok.Data;

import java.util.List;

/**
 * @author cafababe
 * 获取用户公开数据的表单
 */
@Data
public class GetPublicDetailForm {
    private List<Integer> ids;
}
