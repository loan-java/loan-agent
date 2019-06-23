
import com.mod.loan.util.rongze.HttpClientUtils;
import org.junit.Test;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import javax.annotation.Resource;

public class ControllerTest extends BaseSpringBootJunitTest {

    @Resource
    private TestRestTemplate restTemplate;

    @Test
    public void certAuth() {
        String param = "{\"method\":\"fund.cert.auth\",\"sign\":\"Vs6jHKpVhHe7YWtu/U7q23Ch6zFOrVoOGDsM3clDdN8fAyKM0k3dSOQmujF5RqfpgxG5qlKMkz2VMg+God/qYCdoiPxrg8y6LqVCCC9sGuSJZv92Br/ZNUQhQWTkmd3WVWD6QxdtSQn1Y1nqiC2nnTmEOti1IuD5kuI3MG5DxpY=\",\"merchant_id\":\"Hsd0515\",\"des_key\":\"U8hwcs2zkpeLoDHQtkji+C081eHIdYEkhdwmsFSOb96e1Yt2OmanwpVLrFvimhe4pKNZeYTDNGapu3XXowSgtIFJ9gZraJUloPd9SuJ0twAPtzlxOk3+p9dGnSg1OqTzRH72L8PFlRRagguDXajDS6iraQqI9NQBvVyPVfNkd1A=\",\"biz_data\":\"WAV+oSgtqjqUQPCFRrGLfW9DQLIlJn7NxHWWUl2GSP7TXNI9nPfek0vR4hxKIEuxUD3smOK0AdpsGzebr1R3oM35BBPXEzE65q84nhjzbncaQUAtYE2ek98quOh/f2uoa6Uqied88+W7NYr1p9qz08Cd8+2TLJq5d09mo5Oel/NZJnr+Wv30Wgjmv0+b0INOQky0nemEasG51HrKFOjYWsfX8fqvjoZZS0nbzSkixAVmQh7ZEsJ6USVgNHvf6vPx\",\"biz_enc\":\"1\"}";
        post(param);
    }

    @Test
    public void userBase() throws Exception {
        String param="{\"method\":\"fund.userinfo.base\",\"sign\":\"tMw3PqVWxWjFCqLszwfVKNx228DK+t/qiJ4krUKeuhtnbbiDCulQr9i/99/KGAZfilbStOKNzCcc7otZ8XQ9CqyHDgJuwqJIEoAiEw/7zGMS/V/vuJOI57XQEoQrYGH0ykrzE96Q4h+XtFQKD6844TYOip+OLYQ2pARs1wVg4h8=\",\"merchant_id\":\"Hsd0515\",\"des_key\":\"rpH8ubJCDTepJcXXWvCYBY0d6OrGaryT0k1xa7dMVF3ay1fPDMQO5avzs2KT4kjFiXH8ygWazDj5JAcB2Pm4BXdSxi7Tk32U6TlnRDE5G2tfs7o+/jz6gDJfIoNrOFi6zXPv8S3+AzOIBpAlXgy5IPgjhb8ro8SA4iMuRYWZYv0=\",\"biz_data\":\"oId7Kwv3glT+VythxUzInUhThU0VHFd8iuarPBkZ1p3X2CSX+Oen9G8xXU/Ri4X6ezOnL/eLLIwMP48qZ6Fqx4+Lr0X6uOHbwMxwkL8fkBErO1yoskvzWaXQNMMiLOswwN8icKs8OOGC3RXtpjeRMVL6c0ISF5VcjyhATOW6BzTgj0eNyHSLxyBGlrjzXpliuwUSHCXbK1FkQlqyUHfmq4Xb26XwhkrgzHXA/WlBtiQLxLPW9qeOdTs+gU4Pkjau99A6+IjAhftH1Uov15Z+djHeUnlUiBSGn9mF2VCl/QnpubmbJOr/9UK7g7VR7YZtvJwyClLijZfRi5sd6hwiFkVDvIGWbuvDke0qA2ha+Ajo+gBPwBgJaJJc5Bb1AAhpgT0gLOl2DWWrNn7JIJ9FRek93RoIp5HNuOHH9JvG48+y+zVrkcshYfuUGwKJgQXdILTO3RWJsFjQDzDBobmq2ck0wXs8bk7Lv4wi+RtnMIyo2dilLIWvon0/+0KZXR5FO2eF4ByruHavd8jQRMnTbjqqW/J1PZ5c6sl5nbt0xT1SB6pSR4b1xxLfbjz6wNVQ8QkAPk0d09fDFkuOObqdAxjbQGusA89MDpdj7iNYqbNV5ffTRN3yXl1CbkfVBPr21dvp38KU2g08l2Le4UiB/yx45PbvFpYRDxF/3HuB+udtJw1O0g0sQ3+JD0vIvgaywTZOzg6+tGh5Oip5O52jrWHmHH4rXJ4v8HkQMjfY2QDWBuKm2Q2llQMcW12GsAwmNa2JB6UsgDIyFukGt8fUdNkQ7av3HBuQ2eediCvTKpzcp9yqvnqxImRkKjLbb8Qi3QMoAEZnPkamGFudas2JL2aqMaooUcI9fogmJ2v/fD8=\",\"biz_enc\":\"1\"}";
        post(param);
    }

    private String post(String param) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.valueOf("application/json;UTF-8"));
        HttpEntity<String> strEntity = new HttpEntity<>(param, headers);

        String body = restTemplate.postForObject("/rongze/dispatcherRequest", strEntity, String.class);
        System.out.println(body);
        return body;
    }

}
