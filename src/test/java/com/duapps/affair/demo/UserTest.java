package com.duapps.affair.demo;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import com.alibaba.fastjson.JSONObject;
import com.duapps.affair.demo.bean.R;
import com.duapps.affair.demo.bean.Req;
import com.duapps.affair.demo.bean.UserInfo;
import com.duapps.affair.demo.dao.UserDao;
import com.duapps.affair.demo.service.UserService;
import com.duapps.affair.demo.util.UpdateUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.AbstractIterator;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Invoice;
import com.stripe.model.Subscription;
import org.apache.commons.lang3.builder.ToStringExclude;
import org.assertj.core.util.Lists;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

/**
 * @Author he.zhou
 * @Date 2020/5/20
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class UserTest {

    private static final String STRIPE_API_KEY = "sk_test_YAKzjIWutb126ThI7A8Mq5Oz00edbDMlVL";


    @Resource
    private UserDao userDao;

    @Resource
    private UserService userService;

    @Test
    public void test() {
        UserInfo userInfo = userService.getById(1L);
        System.out.println(JSONObject.toJSONString(userInfo));
        System.out.println(userInfo.getUpdateTime());

//        UserInfo userInfo1 = new UserInfo();
//        userInfo1.setUserId("23232");
//        userInfo1.setNickName("323232");
//        userInfo1.setAvatar("23232");
//        userInfo1.setOpenId("2323");
//
//        UserInfo userInfo2 = new UserInfo();
//
//        UpdateUtil.copyNullProperties(userInfo1, userInfo2);
//        userDao.save(userInfo2);
    }

    @Test
    public void test1() throws JsonProcessingException {
//        UserInfo userInfo = userService.getByNickName("232323");
//        UserInfo userInfo1 = UserInfo.builder()
//                .userId("323").nickName("232323").build();
//        UserInfo userInfo2 = UserInfo.builder()
//                .userId("11").nickName("3434").build();
//        UserInfo userInfo3 = UserInfo.builder()
//                .userId("22").nickName("3434").build();
//        UserInfo userInfo4 = UserInfo.builder()
//                .userId("22")
//                .nickName("34344").build();
//        userDao.saveAll(Lists.newArrayList(userInfo1, userInfo2, userInfo3,userInfo4));

//        System.out.println(R.SHARED_MAPPER.writeValueAsString(userInfo));

        userDao.updateByNme("测试", "测试", Lists.newArrayList(1, 2, 3));
    }

    @Test
    public void pay() throws StripeException, JsonProcessingException {
        Stripe.apiKey = STRIPE_API_KEY;
        //in_1GmENPJ5srMm5H85EAjowevb
        //in_1GmEaxJ5srMm5H85kCF5mrbu
        //"id": "in_1GmEaxJ5srMm5H85kCF5mrbu",
        Invoice invoice = Invoice.retrieve("in_1GmELiJ5srMm5H85B6RcRDsD");
        Invoice invoice2 = invoice.pay();
        System.out.println(JSONObject.toJSONString(invoice2));
    }

    @Test
    public void getSub() throws StripeException {
        String sub = "sub_HKWv56wpwgNi3g";
        Stripe.apiKey = "sk_test_YAKzjIWutb126ThI7A8Mq5Oz00edbDMlVL";

        Subscription subscription = Subscription.retrieve("sub_HKWv56wpwgNi3g");
        System.out.println(JSONObject.toJSONString(subscription));
    }

    @Test

    public void pageTest() {
        UserInfo userInfo = new UserInfo();
        userInfo.setNickName("32323");

        ExampleMatcher matcher = ExampleMatcher.matching()
                .withIgnorePaths("id");
        Example<UserInfo> example = Example.of(userInfo, matcher);

        long count = userDao.count(example);


        Page<UserInfo> page = userDao.findAll(example, PageRequest.of(0, 4, Sort.Direction.DESC, "id"));

        System.out.println(JSONObject.toJSONString(page));
    }


    @Test
    public void queryAll() {
        Req req = Req.builder().currentPage(1)
                .nickName("32323").pageSize(3).build();

//        test2(req).forEach(ar -> {
//            ar.forEach(a -> System.out.println(a.getId()));
//        });

        System.out.println(StreamSupport.stream(test2(req).spliterator(), false)
                .flatMap(Collection::stream)
                 .count());

    }


    public Iterable<List<UserInfo>> test2(Req req) {

        return () -> new AbstractIterator<List<UserInfo>>() {
            boolean end = false;

            @Override
            protected List<UserInfo> computeNext() {
                if (end) {
                    return endOfData();
                }

                List<UserInfo> list = userService.queryAllByEx(req);

                if (list.size() < req.getPageSize()) {
                    end = true;
                }
                if (CollectionUtils.isEmpty(list)) {
                    return endOfData();
                }
                req.setCurrentPage(req.getCurrentPage() + 1);
                return list;
            }
        };
    }


}
