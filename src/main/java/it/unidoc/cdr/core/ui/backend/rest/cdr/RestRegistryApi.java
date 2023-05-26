package it.unidoc.cdr.core.ui.backend.rest.cdr;

import it.unidoc.cdr.core.ui.backend.rest.cdr.common.RestPageRequest;
import it.unidoc.cdr.core.ui.backend.rest.cdr.data.DocumentData;
import it.unidoc.cdr.core.ui.backend.rest.cdr.data.ErrorData;
import it.unidoc.cdr.core.ui.backend.rest.cdr.data.FolderData;
import it.unidoc.cdr.core.ui.backend.rest.cdr.data.SubmissionData;
import it.unidoc.cdr.core.ui.backend.rest.cdr.data.result.ResultDataWrapper;

import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.Set;


/**
 * @author b.amoruso
 */

@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public interface RestRegistryApi {

    @GET
    @Path("/getsubmission")
    ResultDataWrapper<SubmissionData> getsubmission(
            @QueryParam("asupdate") boolean asupdate,
            @QueryParam("begin") String begin,
            @QueryParam("end") String end,
            @BeanParam @Valid RestPageRequest filters);

    @GET
    @Path("/getfolder")
    ResultDataWrapper<FolderData> getfolder(
            @QueryParam("asupdate") boolean asupdate,
            @QueryParam("begin") String begin,
            @QueryParam("end") String end,
            @BeanParam @Valid RestPageRequest filters);


    @GET
    @Path("/getdocument")
    ResultDataWrapper<DocumentData> getdocument(
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

    @DELETE
    @Path("/removesubmission")
    void removesubmission(Set<String> items);

    @DELETE
    @Path("/removedocument")
    void removedocument(Set<String> items);

    @DELETE
    @Path("/removefolder")
    void removefolder(Set<String> items);

    @DELETE
    @Path("/removeerror")
    void removeerror(Set<String> items);

}
