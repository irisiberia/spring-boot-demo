package com.duapps.affair.demo.service;


import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URI;

/**
 * @Author he.zhou
 * @Date 2020-06-04
 */
public class WechatLoginService {

    /**
     * 微信登陆通过code获取accessToken
     *
     * @param appId
     * @param userAppSecret
     * @param code
     * @return
     * @throws Exception
     */
    public StringBuilder getAccessTokenBycode(String appId, String userAppSecret, String code) throws Exception {
        //查看官方文档 https://open.weixin.qq.com/cgi-bin/showdocument?action=dir_list&t=resource/res_list&verify=1&id=open1419317853&token=&lang=
        String url = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=" + appId + "&secret=" +
                userAppSecret + "&code=" + code + "&grant_type=authorization_code";
        URI uri = URI.create(url);
        HttpClient client = HttpClients.createDefault();
        HttpGet get = new HttpGet(uri);
        HttpResponse response = client.execute(get);
        StringBuilder sb = new StringBuilder();
        if (response.getStatusLine().getStatusCode() == 200) {
            HttpEntity entity = response.getEntity();
            BufferedReader reader = new BufferedReader(new InputStreamReader(entity.getContent(), "UTF-8"));
            for (String temp = reader.readLine(); temp != null; temp = reader.readLine()) {
                sb.append(temp);
            }
        }
        return sb;
    }


    /**
     * access_token是否有效的验证
     *
     * @param accessToken
     * @param openID
     * @return
     */
    public boolean isAccessTokenIsInvalid(String accessToken, String openID) throws Exception {
        String url = "https://api.weixin.qq.com/sns/auth?access_token=" + accessToken + "&openid=" + openID;
        URI uri = URI.create(url);
        HttpClient client = HttpClients.createDefault();
        HttpGet get = new HttpGet(uri);
        HttpResponse response = client.execute(get);
        if (response.getStatusLine().getStatusCode() == 200) {
            HttpEntity entity = response.getEntity();

            BufferedReader reader = new BufferedReader(new InputStreamReader(entity.getContent(), "UTF-8"));
            StringBuilder sb = new StringBuilder();

            for (String temp = reader.readLine(); temp != null; temp = reader.readLine()) {
                sb.append(temp);
            }
            JSONObject object = JSONObject.parseObject(sb.toString().trim());
            int errcode = object.getInteger("errcode");
            if (errcode == 0) {
                //未失效
                return true;
            }
        }
        return false;
    }

    /**
     * access_token       接口调用凭证
     * expires_in        access_token接口调用凭证超时时间，单位（秒）
     * refresh_token     用户刷新access_token
     * openid           授权用户唯一标识
     * scope          用户授权的作用域，使用逗号（,）分隔
     *
     * @param APP_ID
     */
    public JSONObject refreshAccessToken(String APP_ID, String refreshToken) throws Exception {
        /**
         * access_token是调用授权关系接口的调用凭证，由于access_token有效期（目前为2个小时）较短，当access_token超时后，可以使用refresh_token进行刷新，access_token刷新结果有两种：
         *
         * 1.若access_token已超时，那么进行refresh_token会获取一个新的access_token，新的超时时间；
         *
         * 2.若access_token未超时，那么进行refresh_token不会改变access_token，但超时时间会刷新，相当于续期access_token。
         *
         * refresh_token拥有较长的有效期（30天）且无法续期，当refresh_token失效的后，需要用户重新授权后才可以继续获取用户头像昵称。
         */
        String uri = "https://api.weixin.qq.com/sns/oauth2/refresh_token?appid=" + APP_ID + "&grant_type=refresh_token&refresh_token=" + refreshToken;
        HttpClient client = HttpClients.createDefault();
        HttpGet get = new HttpGet(URI.create(uri));
        HttpResponse response = client.execute(get);
        JSONObject object = new JSONObject();
        if (response.getStatusLine().getStatusCode() == 200) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent(), "UTF-8"));
            StringBuilder builder = new StringBuilder();
            for (String temp = reader.readLine(); temp != null; temp = reader.readLine()) {
                builder.append(temp);
            }
            object = JSONObject.parseObject(builder.toString().trim());
        }
        return object;
    }

    /**
     * 得到用户基本信息
     *
     * @param accessToken
     * @param openId
     * @param tClass
     * @return
     * @throws Exception
     */
    public <T> T getAppWeiXinUserInfo(String accessToken, String openId, Class<T> tClass) throws Exception {
        String uri = "https://api.weixin.qq.com/sns/userinfo?access_token=" + accessToken + "&openid=" + openId;
        HttpClient client = HttpClients.createDefault();
        HttpGet get = new HttpGet(URI.create(uri));
        HttpResponse response = client.execute(get);

        if (response.getStatusLine().getStatusCode() == 200) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent(), "UTF-8"));
            StringBuilder builder = new StringBuilder();
            for (String temp = reader.readLine(); temp != null; temp = reader.readLine()) {
                System.out.println(temp);
                builder.append(temp);
            }
            return JSONObject.parseObject(builder.toString(), tClass);
        }
        return null;
    }

}
