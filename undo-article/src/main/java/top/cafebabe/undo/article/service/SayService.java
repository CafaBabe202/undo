package top.cafebabe.undo.article.service;

import org.springframework.stereotype.Service;
import top.cafebabe.undo.article.bean.Say;
import top.cafebabe.undo.article.dao.SayMapper;
import top.cafebabe.undo.common.util.NowUtil;
import top.cafebabe.undo.common.util.StringUtil;

import java.util.List;

/**
 * @author cafababe
 */
@Service
public class SayService {
    final SayMapper mapper;

    public SayService(SayMapper mapper) {
        this.mapper = mapper;
    }

    public boolean say(int userId, int articleId, String content) {
        Say say = new Say();
        say.setArticleId(articleId);
        say.setContent(content);
        say.setUserId(userId);
        say.setTime(StringUtil.formatData(NowUtil.now()));
        return mapper.say(say) == 1;
    }

    public List<Say> getAll(int articleId) {
        return mapper.getAllSay(articleId);
    }
}
