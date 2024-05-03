package hello.kpop.socialing.common;

import jakarta.servlet.http.HttpServletRequest;
import lombok.Data;
import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

//페이지 네이션 ( 소셜 , 공지 ) 공통
@Data
public class Pagination {

    private int page; //현제 페이지
    private int total; // 전체 레코드 개수
    private int ranges; // 페이지 구간 개수
    private int limit; // 1 페이지 당 레코드 개수

    private int firstRangePage; // 구간별 첫 페이지 -- 구간별 시작 페이지
    private int lastRangePage; // 구간별 마지막 페이지  -- 구간별 종료 페이지

    private int prevRangePage; // 이전 구간 첫 페이지 번호
    private int nextRangePage; // 다음 구간 첫 페이지 번호

    private int totalPages; // 전체 페이지 갯수
    private String baseURL; // 페이징 쿼리스트링 기본 URL

//    기본값이 있는 페이지
//    private int page =1;
//    private int total;
//    private int ranges = 10;
//    private int limit =20;



    /**
     * @param page : 현재 페이지 -- 페이지 번호 (1,2,3, ...)
     * @param total : 전체 레코드 갯수 -- 전체 레코드 개수
     * @param ranges : 페이지 구간 갯수 -- 페이지 구간별 페이지 번호 개수
     * @param limit : 1페이지 당 레코드 갯수 -- 레코드 개수
     */
    public Pagination(int page, int total, int ranges, int limit, HttpServletRequest request) {

        page = ProcessUtils.onlyPositiveNumber(page, 1);
        total = ProcessUtils.onlyPositiveNumber(total, 0);
        ranges = ProcessUtils.onlyPositiveNumber(ranges, 10);
        limit = ProcessUtils.onlyPositiveNumber(limit, 20);

        // 전체 페이지 갯수
        // 전체 페이지 개수 = 전체 페이지 개수 / 레코드 개수(올림 처리)
        int totalPages = (int)Math.ceil(total / (double)limit);

        /*
        * ex)5
        * 1,  2,  3,  4,  5
        * 0, 0.2, 0.4, 0.6, 0.8
        *
        * 6,  7,  8,  9,  10
        * 1, 1.2, 1.4, 1.6, 1.8
        * (( 현재 페이지 -1 ) / 5) ---- 구간 번호
        * */

        // 구간 번호
        int rangeCnt = (page - 1) / ranges;
        int firstRangePage = rangeCnt * ranges + 1;
        int lastRangePage = firstRangePage + ranges - 1;
        lastRangePage = lastRangePage > totalPages ? totalPages : lastRangePage;

        /*
        * 이전 구간 첫번째 페이지( < 버튼이 첫번쨰 페이지일시 노출 X)
        *   -> 첫번째 구간이 아닐때 만 노출
        *
        * 다음 구간 첫번째 페이지 ( > 버튼이 마지막 페이지일시 노출 X)
        *   -> 마지막 구간이 아닐때만 노출
        * */


        // 이전 구간 첫번째 페이지
        if (rangeCnt > 0) {
            prevRangePage = firstRangePage - ranges;
        }

        // 다음 구간 첫번째 페이지
        // 마지막 구간 번호
        int lastRangeCnt = (totalPages - 1) / ranges;
        if (rangeCnt < lastRangeCnt) { // 마지막 구간이 아닌 경우 다음 구간 첫 페이지 계산
            nextRangePage = firstRangePage + ranges;
        }

        /*
         * 쿼리스트링 값 유지 처리 - 쿼리스트링 값 중에서 page만 제외하고 다시 조합
         *  예) ?status=STATUS&name=...&page=2 -> ?status=STATUS&name=...
         *      ?page=2 -> ?
         *      없는 경우 -> ?
         *
         *      &로 문자열 분리
         *      { "status=STATUS", "name=....", "page=2" }
         */
        // 지워지는 쿼리스트링값을 유지 하기 위한 처리
        String baseURL = "?";
        if (request != null) {
            String queryString = request.getQueryString();
            if (StringUtils.hasText(queryString)) {
                queryString = queryString.replace("?", "");

                baseURL += Arrays.stream(queryString.split("&"))
                        .filter(s -> !s.contains("page="))
                        .collect(Collectors.joining("&"));

                baseURL = baseURL.length() > 1 ? baseURL += "&" : baseURL;
            }
        }

        this.page = page;
        this.total = total;
        this.ranges = ranges;
        this.limit = limit;
        this.firstRangePage = firstRangePage;
        this.lastRangePage = lastRangePage;
        this.totalPages = totalPages;
        this.baseURL = baseURL;
    }

    //쿼리 스트링이 필요 없는 경우 사용
    public Pagination(int page, int total, int ranges, int limit) {
        this(page, total, ranges, limit, null);
    }

    public List<String[]> getPages() {
        // 0 : 페이지 번호, 1 : 페이지 URL - ?page=페이지번호

        return IntStream.rangeClosed(firstRangePage, lastRangePage)
                .mapToObj(p -> new String[] { String.valueOf(p),
                        baseURL + "page=" + p})
                .toList();

    }
}
