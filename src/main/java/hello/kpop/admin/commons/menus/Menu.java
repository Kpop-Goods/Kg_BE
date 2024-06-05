package hello.kpop.admin.commons.menus;

import jakarta.servlet.http.HttpServletRequest;

import java.util.ArrayList;
import java.util.List;

/**
 * 페이지에 보여질 메뉴 유틸 클래스
 *
 */
public class Menu {



    //관리자 사이드 메뉴에 대한 클래스
    public static List<MenuDetail> gets(String code){
        List<MenuDetail> menus = new ArrayList<>();

        if(code.equals("user")){ // 회원 하위 메뉴
            menus.add(new MenuDetail("user","회원 목록" , "/admin/user"));
            menus.add(new MenuDetail("register" , "회원 관리","/admin/user/manage"));
        } else if (code.equals("board")) {// 게시판 하위 메뉴
            menus.add(new MenuDetail("board", "게시판 목록","/admin/board"));
            menus.add(new MenuDetail("register", "게시판 등록","/admin/board/add"));
            menus.add(new MenuDetail("manage" , "게시판 관리","/admin/board/manage"));
        } else if (code.equals("social")) { // 소셜 하위 메뉴
            menus.add(new MenuDetail("social" , "소셜링 목록" , "/admin/social"));
            menus.add(new MenuDetail("register", "소셜링 등록" , "/admin/social/add"));
        } else if (code.equals("artist")) { // 아티스트 하위 메뉴
            menus.add(new MenuDetail("artist" , "아티스트 목록","/admin/artist"));
            menus.add(new MenuDetail("register" , "아티스트 등록" , "/admin/artist/add"));
        } else if (code.equals("agency")){ // 소속사 하위 메뉴
            menus.add(new MenuDetail("agency" , "소속사 목록" , "/admin/agency"));
            menus.add(new MenuDetail("register" ,"소속사 등록" , "/admin/agency/add"));
        }
        return menus;
    }

    public static String getSubMenuCode(HttpServletRequest request){
        String URI  = request.getRequestURI();

        return URI.substring(URI.lastIndexOf('/')+1);

    }

}


