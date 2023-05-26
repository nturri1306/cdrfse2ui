package it.unidoc.cdr.core.ui.backend.rest.fsebroker;

import it.unidoc.cdr.core.ui.backend.rest.fsebroker.common.StatusData;
import it.unidoc.cdr.core.ui.backend.rest.fsebroker.common.TypeWrapper;
import it.unidoc.cdr.core.ui.backend.rest.fsebroker.common.XADCodes;
import it.unidoc.cdr.core.ui.backend.rest.fsebroker.type.*;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.io.InputStream;
import java.util.Collection;

/**
 * @author b.amoruso
 */

@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public interface RestInterfaceApi {

    @GET
    @Path("/xad")
    public ApiResponse<Collection<XADCodes>> xad();

    @GET
    @Path("/status")
    public ApiResponse<StatusData> status();

    @GET
    @Path("/uuid")
    public ApiResponse<Collection<SearchType>> uuid(
            @QueryParam("taxcode") String taxCode,
            @QueryParam("identifier") String identifier);

    @GET
    @Path("/search")
    public ApiResponse<Collection<SearchType>> search(
            @QueryParam("taxcode") String taxCode);

    @POST
    @Path("/password")
    public ApiResponse<Boolean> password(String password);

    @GET
    @Path("/submit")
    public ApiResponse<Collection<TypeWrapper<SubmitType>>> submit(
            @QueryParam("begin") String begin,
            @QueryParam("end") String end,
            @QueryParam("state") String state);
//		@QueryParam("filtered") boolean filtered);

    @DELETE
    @Path("/removesubmit/{id}")
    public ApiResponse<String> removesubmit(
            @PathParam("id") String id);

    @GET
    @Path("/retrievesubmit/{id}")
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    public InputStream retrievesubmit(
            @PathParam("id") String id);

    @GET
    @Path("/update")
    public ApiResponse<Collection<TypeWrapper<UpdateType>>> update(
            @QueryParam("begin") String begin,
            @QueryParam("end") String end,
            @QueryParam("state") String state);
//		@QueryParam("filtered") boolean filtered);

    @DELETE
    @Path("/removeupdate/{id}")
    public ApiResponse<String> removeupdate(
            @PathParam("id") String id);

    @GET
    @Path("/outcome")
    public ApiResponse<Collection<TypeWrapper<OutcomeType>>> outcome(
            @QueryParam("begin") String begin,
            @QueryParam("end") String end,
            @QueryParam("state") String state);
//		@QueryParam("filtered") boolean filtered);

    @DELETE
    @Path("/removeoutcome/{id}")
    public ApiResponse<String> removeoutcome(
            @PathParam("id") String id);

    @GET
    @Path("/consent")
    public ApiResponse<Collection<TypeWrapper<ConsentType>>> consent(
            @QueryParam("begin") String begin,
            @QueryParam("end") String end,
            @QueryParam("state") String state);
//		@QueryParam("filtered") boolean filtered);

    @DELETE
    @Path("/removeconsent/{id}")
    public ApiResponse<String> removeconsent(
            @PathParam("id") String id);

    @GET
    @Path("/consentState")
    public ApiResponse<Collection<TypeWrapper<ConsentStateType>>> consentState(
            @QueryParam("begin") String begin,
            @QueryParam("end") String end,
            @QueryParam("state") String state);
//		@QueryParam("filtered") boolean filtered);

    @DELETE
    @Path("/removeconsentState/{id}")
    public ApiResponse<String> removeconsentState(
            @PathParam("id") String id);

    @GET
    @Path("/delete")
    public ApiResponse<Collection<TypeWrapper<DeleteType>>> delete(
            @QueryParam("begin") String begin,
            @QueryParam("end") String end,
            @QueryParam("state") String state);
//		@QueryParam("filtered") boolean filtered);

    @DELETE
    @Path("/removedelete/{id}")
    public ApiResponse<String> removedelete(
            @PathParam("id") String id);

}
