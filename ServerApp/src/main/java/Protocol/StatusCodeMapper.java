package Protocol;

public class StatusCodeMapper {
    public static String mapToStatusCode(StatusCode statusCode) {
        if (statusCode == StatusCode.OK)
            return "200";
        if (statusCode == StatusCode.BAD_REQUEST)
            return "400";
        if (statusCode == StatusCode.UNAUTHORIZED)
            return "401";
        if (statusCode == StatusCode.FORBIDDEN)
            return "403";
        if (statusCode == StatusCode.NOT_FOUND)
            return "404";
        return "500";
    }

    public static StatusCode mapToEnum(String statusCode){
        if (statusCode.equals("200"))
            return StatusCode.OK;
        if (statusCode.equals("400"))
            return StatusCode.BAD_REQUEST;
        if (statusCode.equals("401"))
            return StatusCode.UNAUTHORIZED;
        if (statusCode.equals("403"))
            return StatusCode.FORBIDDEN;
        if (statusCode.equals("404"))
            return StatusCode.NOT_FOUND;
        return StatusCode.SERVER_ERROR;
    }
}
