package it.unidoc.cdr.core.ui.backend.rest.cdr;

import it.unidoc.cdr.core.ui.backend.rest.cdr.common.RestPageRequest;
import it.unidoc.cdr.core.ui.backend.rest.cdr.data.DocumentData;
import it.unidoc.cdr.core.ui.backend.rest.cdr.data.DocumentFileData;
import it.unidoc.cdr.core.ui.backend.rest.cdr.data.ErrorData;
import it.unidoc.cdr.core.ui.backend.rest.cdr.data.ForwardData;
import it.unidoc.cdr.core.ui.backend.rest.cdr.data.result.ResultDataWrapper;

import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;
import java.util.Set;

/**
 * TODO
 *
 * @author b.amoruso
 */

@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public interface RestRepositoryApi {

    @GET
    @Path("/getdocument")
    ResultDataWrapper<DocumentFileData> getdocument(
            @QueryParam("asupdate") boolean asupdate,
            @QueryParam("begin") String begin,
            @QueryParam("end") String end,
            @BeanParam @Valid RestPageRequest filters);

    @GET
    @Path("/geterror")
    ResultDataWrapper<ErrorData> geterror(
            @QueryParam("asupdate") boolean asupdate,
            @QueryParam("begin") String begin,
            @QueryParam("end") String end,
            @BeanParam @Valid RestPageRequest filters);

    @GET
    @Path("/getforward")
    ResultDataWrapper<ForwardData> getforward( /*incomplete ResultForwardDTO*/
            @QueryParam("asupdate") boolean asupdate,
            @QueryParam("begin") String begin,
            @QueryParam("end") String end,
            @BeanParam @Valid RestPageRequest filters);

    @DELETE
    @Path("/removedocument")
    void removedocument(Set<String> items);

    @DELETE
    @Path("/removeerror")
    void removeerror(Set<String> items);

    @DELETE
    @Path("/removeforward")
    void removeforward(Set<String> items);

    @POST
    @Path("/forward")
    public void forward(String id,
        @QueryParam("removesource") boolean removeSource);

    @POST
    @Path("/bulkforward")
    void bulkforward(
        @QueryParam("removesource") boolean removeSource,
        @QueryParam("asupdate") boolean asupdate,
        @QueryParam("begin") String begin,
        @QueryParam("end") String end,
        @QueryParam("filter") List<String> filter);

}
