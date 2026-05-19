package roomescape.global.auth;

public record MemberPrincipal(String name) {

    public static final String SESSION_KEY = "memberPrincipal";
}
