package ir.newims.ims.business.personnel.request.dto.request;

public class RequestListRequest {
    private Integer pageNum;
    private Integer perPage;

    public Integer getPageNum() {
        return pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public Integer getPerPage() {
        return perPage;
    }

    public void setPerPage(int perPage) {
        this.perPage = perPage;
    }
}
