package com.qz.admin.service;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.IdUtil;
import com.qz.admin.common.PageResult;
import com.qz.admin.entity.Consult;
import com.qz.admin.repository.ConsultRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ConsultService {

    @Autowired
    private ConsultRepository consultRepository;

    /**
     * 创建咨询（H5 公众站匿名提交）。自动补全 id、订单号、创建时间、默认状态与来源。
     */
    public Consult create(Consult consult) {
        if (consult.getId() == null || consult.getId().isEmpty()) {
            consult.setId(IdUtil.simpleUUID());
        }
        if (consult.getOrderNo() == null || consult.getOrderNo().isEmpty()) {
            consult.setOrderNo(genOrderNo());
        }
        if (consult.getStatus() == null || consult.getStatus().isEmpty()) {
            consult.setStatus("pending");
        }
        if (consult.getFrom() == null || consult.getFrom().isEmpty()) {
            consult.setFrom("h5");
        }
        consult.setCreateTime(DateUtil.now());
        return consultRepository.save(consult);
    }

    private String genOrderNo() {
        return "QZ" + DateUtil.format(DateUtil.date(), "yyyyMMddHHmmss") + (int) (Math.random() * 9000 + 1000);
    }

    public PageResult<Consult> page(String keyword, String status, int page, int pageSize) {
        List<Consult> all = consultRepository.findAll().stream()
                .filter(c -> match(c, keyword, status))
                .sorted(Comparator.comparing(Consult::getCreateTime, Comparator.reverseOrder()))
                .collect(Collectors.toList());
        return paginate(all, page, pageSize);
    }

    public void updateStatus(String id, String status) {
        Consult c = consultRepository.findById(id).orElse(null);
        if (c == null) {
            throw new IllegalArgumentException("数据不存在或已被删除");
        }
        c.setStatus(status);
        consultRepository.save(c);
    }

    public void delete(String id) {
        if (!consultRepository.existsById(id)) {
            throw new IllegalArgumentException("数据不存在或已被删除");
        }
        consultRepository.deleteById(id);
    }

    private boolean match(Consult c, String keyword, String status) {
        if (status != null && !status.isEmpty() && !status.equals(c.getStatus())) {
            return false;
        }
        if (keyword == null || keyword.trim().isEmpty()) {
            return true;
        }
        String k = keyword.trim().toLowerCase();
        return (c.getName() != null && c.getName().toLowerCase().contains(k))
                || (c.getPhone() != null && c.getPhone().contains(k))
                || (c.getOrderNo() != null && c.getOrderNo().toLowerCase().contains(k))
                || (c.getCountry() != null && c.getCountry().toLowerCase().contains(k));
    }

    private PageResult<Consult> paginate(List<Consult> all, int page, int pageSize) {
        int total = all.size();
        int from = (page - 1) * pageSize;
        if (from > total) {
            from = total;
        }
        int to = Math.min(from + pageSize, total);
        return new PageResult<>(all.subList(from, to), total, page, pageSize);
    }
}
