package com.garden.back.weather.infra.api.open;

import com.garden.back.global.MockTestSupport;
import com.garden.back.weather.infra.api.NaverAndOpenAPISupport;
import com.garden.back.weather.infra.api.open.response.WeatherForecastResponse;
import com.garden.back.weather.infra.api.open.response.WeekWeatherResponse;
import com.garden.back.weather.infra.response.AllRegionsWeatherInfo;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

class NaverAndOpenAPISupportTest extends MockTestSupport {

    @InjectMocks
    NaverAndOpenAPISupport naverAndOpenAPISupport;

    ZonedDateTime now = ZonedDateTime.now(ZoneId.of("Asia/Seoul"));

    @DisplayName("기상청으로 부터 받은 응답 중 PTY, T1H 만을 필터링 해서 하늘 상태, 온도를 얻을 수 있다.")
    @Test
    void parseWeatherForecastResponse() {
        //given
        String regionName = "서울특별시";
        Region region = new Region(60, 127, regionName, "11B00000");
        String temperature = "20";
        String skyValue = "맑음";

        //2번 째 아이템의 ABC 카테고리는 필터링 되는지 검증
        WeatherForecastResponse response = sut.giveMeBuilder(WeatherForecastResponse.class)
            .size("response.body.items.item", 3)
            .set("response.body.items.item[0].category", "PTY")
            .set("response.body.items.item[0].obsrValue", "0") // 맑음
            .set("response.body.items.item[0].baseTime", "0600")
            .set("response.body.items.item[0].nx", 60)
            .set("response.body.items.item[0].ny", 127)
            .set("response.body.items.item[1].category", "T1H")
            .set("response.body.items.item[1].obsrValue", temperature) // 20도
            .set("response.body.items.item[1].baseTime", "0600")
            .set("response.body.items.item[1].nx", 60)
            .set("response.body.items.item[1].ny", 127)
            .set("response.body.items.item[2].category", "ABC")
            .set("response.body.items.item[2].obsrValue", temperature) // 20도
            .set("response.body.items.item[2].baseTime", "0600")
            .set("response.body.items.item[2].nx", 60)
            .set("response.body.items.item[2].ny", 127)
            .sample();

        //when
        AllRegionsWeatherInfo actualInfo = naverAndOpenAPISupport.parseWeatherForecastResponse(region, response);

        //then
        AllRegionsWeatherInfo expectedInfo = new AllRegionsWeatherInfo(regionName, skyValue, temperature);
        assertThat(actualInfo).isEqualTo(expectedInfo);
    }

    @Test
    @DisplayName("현재 시간에 따라 오늘 새벽 6시 또는 어제 새벽 6시의 주간예보 기준일 반환한다.")
    void testGetBaseDateForWeeklyForecast() {
        // given 현재 시간을 가져온다

        String actual = naverAndOpenAPISupport.getBaseDateForWeeklyForecast();

        // when
        String expected;
        if (now.getHour() < 6) {
            // 예상되는 결과는 전날의 날짜
            expected = now.minusDays(1).format(DateTimeFormatter.ofPattern("yyyyMMdd"));

            // then
        } else {
            // 현재 시간이 오전 6시 이후인 경우
            // 예상되는 결과는 현재 날짜
            //then
            expected = now.format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        }
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    @DisplayName("가장 가까운 과거의 예보 시간을 반환한다.")
    void getNearestForecastDateTime() {
        // given
        LocalDateTime testTime = LocalDateTime.of(2023, 12, 15, 9, 30); // Example date and time

        // when
        String actual = naverAndOpenAPISupport.getNearestForecastDateTime(testTime);

        // then
        LocalDateTime expectedTime = LocalDateTime.of(2023, 12, 15, 5, 0); // The expected nearest time
        String expected = expectedTime.format(DateTimeFormatter.ofPattern("yyyyMMddHHmm"));
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    @DisplayName("기상청으로 부터 받은 응답 중 TMP, PTY 카테고리의 미래 시간 데이터만을 필터링한다.")
    void testFilterForecastData() {
        // given: 1시간 전, 1시간 이후의 데이터를 받음

        String todayDate = now.format(DateTimeFormatter.BASIC_ISO_DATE);
        String yesterdayDate = now.minusDays(1).format(DateTimeFormatter.BASIC_ISO_DATE);
        String futureTimeFormatted = now.plusHours(1).format(DateTimeFormatter.ofPattern("HHmm"));
        String pastTimeFormatted = now.minusHours(1).format(DateTimeFormatter.ofPattern("HHmm"));

        WeekWeatherResponse weekWeatherResponse = sut
            .giveMeBuilder(WeekWeatherResponse.class)
            .size("response.body.items.item", 4)
            .set("response.body.items.item[0].category", "PTY")
            .set("response.body.items.item[0].fcstDate", todayDate)
            .set("response.body.items.item[0].fcstTime", futureTimeFormatted)
            .set("response.body.items.item[0].fcstValue", "0") // 맑음
            .set("response.body.items.item[1].category", "ABC") // 필터링 해야 하는 값
            .set("response.body.items.item[1].fcstDate", todayDate)
            .set("response.body.items.item[1].fcstTime", futureTimeFormatted)
            .set("response.body.items.item[1].fcstValue", "0")
            .set("response.body.items.item[2].category", "TMP")
            .set("response.body.items.item[2].fcstDate", todayDate)
            .set("response.body.items.item[2].fcstTime", futureTimeFormatted)
            .set("response.body.items.item[2].fcstValue", "20") // 20도
            .set("response.body.items.item[3].category", "TMP")
            .set("response.body.items.item[3].fcstDate", yesterdayDate)
            .set("response.body.items.item[3].fcstTime", pastTimeFormatted) //1시간 이전, 필터링 해야 함
            .set("response.body.items.item[3].fcstValue", "20") // 20도
            .sample();

        // when: filterForecastData 메서드를 호출
        List<WeekWeatherResponse.Response.Body.Items.WeatherItem> filteredItems = naverAndOpenAPISupport.filterForecastData(weekWeatherResponse);

        // then: 필터된 결과를 검증
        WeekWeatherResponse expected = sut
            .giveMeBuilder(WeekWeatherResponse.class)
            .size("response.body.items.item", 2)
            .set("response.body.items.item[0].category", "PTY")
            .set("response.body.items.item[0].fcstDate", todayDate)
            .set("response.body.items.item[0].fcstTime", futureTimeFormatted)
            .set("response.body.items.item[0].fcstValue", "0") // 맑음
            .set("response.body.items.item[1].category", "TMP")
            .set("response.body.items.item[1].fcstDate", todayDate)
            .set("response.body.items.item[1].fcstTime", futureTimeFormatted)
            .set("response.body.items.item[1].fcstValue", "20") // 20도
            .sample();

        assertThat(filteredItems).isEqualTo(expected.response().body().items().item()); // 현재 시간 이후의 PTY, TMP 항목만 필터링되었는지 확인
    }

    @DisplayName("testFilterForecastData를 통해 정제 받은 데이터 중 5개를 뽑아온다.")
    @Test
    void extractTopFiveForecastTimes() {
        //given
        WeekWeatherResponse given = sut
            .giveMeBuilder(WeekWeatherResponse.class)
            .size("response.body.items.item", 7)
            .set("response.body.items.item[0].category", "PTY")
            .set("response.body.items.item[0].fcstDate", "20231215")
            .set("response.body.items.item[0].fcstTime", "1200")
            .set("response.body.items.item[0].fcstValue", "0") // 맑음
            .set("response.body.items.item[1].category", "TMP")
            .set("response.body.items.item[1].fcstDate", "20231215")
            .set("response.body.items.item[1].fcstTime", "1300")
            .set("response.body.items.item[1].fcstValue", "20") // 20도
            .set("response.body.items.item[2].category", "TMP")
            .set("response.body.items.item[2].fcstDate", "20231215")
            .set("response.body.items.item[2].fcstTime", "1400")
            .set("response.body.items.item[2].fcstValue", "20") // 20도
            .set("response.body.items.item[3].category", "TMP")
            .set("response.body.items.item[3].fcstDate", "20231215")
            .set("response.body.items.item[3].fcstTime", "1500")
            .set("response.body.items.item[3].fcstValue", "20") // 20도
            .set("response.body.items.item[4].category", "TMP")
            .set("response.body.items.item[4].fcstDate", "20231215")
            .set("response.body.items.item[4].fcstTime", "1600")
            .set("response.body.items.item[4].fcstValue", "20") // 20도
            .set("response.body.items.item[5].category", "TMP")
            .set("response.body.items.item[5].fcstDate", "20231215")
            .set("response.body.items.item[5].fcstTime", "1700")
            .set("response.body.items.item[5].fcstValue", "20") // 20도
            .set("response.body.items.item[6].category", "TMP")
            .set("response.body.items.item[6].fcstDate", "20231215")
            .set("response.body.items.item[6].fcstTime", "1800")
            .set("response.body.items.item[6].fcstValue", "20") // 20도
            .sample();

        //when
        Set<String> actual = naverAndOpenAPISupport.extractTopFiveForecastTimes(given.response().body().items().item());

        //then
        assertThat(actual).hasSize(5);
    }

    @DisplayName("기상청으로 받은 주간 예보 중 다음 날 12시의 데이터를 꺼내온다.")
    @Test
    void addTomorrowNoonForecast() {
        //given
        String tomorrow = now.plusDays(1).format(DateTimeFormatter.BASIC_ISO_DATE);
        String today = now.format(DateTimeFormatter.BASIC_ISO_DATE);
        String forecastTime = "1200";

        WeekWeatherResponse given = sut
            .giveMeBuilder(WeekWeatherResponse.class)
            .size("response.body.items.item", 3)
            .set("response.body.items.item[0].category", "PTY")
            .set("response.body.items.item[0].fcstDate", today)
            .set("response.body.items.item[0].fcstTime", "1300")
            .set("response.body.items.item[0].fcstValue", "0") // 오늘 데이터
            .set("response.body.items.item[1].category", "TMP")
            .set("response.body.items.item[1].fcstDate", tomorrow)
            .set("response.body.items.item[1].fcstTime", forecastTime)
            .set("response.body.items.item[1].fcstValue", "0")// 내일 12시 데이터
            .set("response.body.items.item[2].category", "PTY")
            .set("response.body.items.item[2].fcstDate", tomorrow)
            .set("response.body.items.item[2].fcstTime", forecastTime)
            .set("response.body.items.item[2].fcstValue", "0") // 내일 12시 데이터
            .sample();

        //when
        List<WeekWeatherResponse.Response.Body.Items.WeatherItem> actual = naverAndOpenAPISupport.addTomorrowNoonForecast(given.response().body().items().item());

        //then
        WeekWeatherResponse expected = sut
            .giveMeBuilder(WeekWeatherResponse.class)
            .size("response.body.items.item", 2)
            .set("response.body.items.item[0].category", "TMP")
            .set("response.body.items.item[0].fcstDate", tomorrow)
            .set("response.body.items.item[0].fcstTime", forecastTime)
            .set("response.body.items.item[0].fcstValue", "0")
            .set("response.body.items.item[1].category", "PTY")
            .set("response.body.items.item[1].fcstDate", tomorrow)
            .set("response.body.items.item[1].fcstTime", forecastTime)
            .set("response.body.items.item[1].fcstValue", "0") // 맑음
            .sample();

        assertThat(actual).isEqualTo(expected.response().body().items().item());
    }
}