package it.unidoc.cdr.core.ui.backend.rest.fsebroker;

import it.unidoc.cdr.core.ui.backend.rest.fsebroker.type.*;
import org.jboss.resteasy.annotations.jaxrs.QueryParam;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

/**
 * @author b.amoruso
 */

@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public interface RestInterfaceService {

    @POST
    @Path("/submit")
    public ServiceResponse submit(SubmitType body, @HeaderParam("userstructure") String structure);

    @POST
    @Path("/submit/{id}")
    public ServiceResponse submit(
            @PathParam("id") String id,
            @QueryParam("remove") boolean remove);

    @POST
    @Path("/batch")
    public ServiceResponse batch(BatchType body);

    @POST
    @Path("/outcome/{id}")
    public ServiceResponse outcome(
            @PathParam("id") String id);

    @POST
    @Path("/outcome")
    public ServiceResponse outcome(OutcomeType body,
                                   @HeaderParam("userstructure") String structure);

    @POST
    @Path("/delete/{id}")
    public ServiceResponse delete(
            @PathParam("id") String id);

    @POST
    @Path("/delete")
    public ServiceResponse delete(DeleteType body,
                                  @HeaderParam("userstructure") String structure);

    @POST
    @Path("/consentState")
    public ServiceResponse contentState(ConsentStateType body,
                                        @HeaderParam("userstructure") String structure);

    @POST
    @Path("/consent")
    public ServiceResponse contentState(ConsentType body,
                                        @HeaderParam("userstructure") String structure);

}
