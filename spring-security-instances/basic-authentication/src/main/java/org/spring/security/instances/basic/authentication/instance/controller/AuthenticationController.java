package org.spring.security.instances.basic.authentication.instance.controller;

import io.swagger.annotations.*;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.spring.security.instances.basic.authentication.instance.core.mapping.MapperAdapter;
import org.spring.security.instances.basic.authentication.instance.model.detail.UserDetail;
import org.spring.security.instances.basic.authentication.instance.service.UserService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.jaas.SecurityContextLoginModule;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.security.auth.login.LoginException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * The type Auth controller.
 *
 * @author Alexander A. Kropotin
 * @project basic -authentication
 * @created 2021 -07-26 09:08 <p>
 */
@Api(
        value = "AuthenticationController",
        description = "Controller for authorization in the system"
)
@Slf4j
@RequiredArgsConstructor
@FieldDefaults(
        level = AccessLevel.PRIVATE,
        makeFinal = true
)
@Validated
@CrossOrigin(origins = "/**")
@RequestMapping(value = "/authentication")
@RestController
public class AuthenticationController {

    /**
     * The User service.
     */
    @Qualifier("SimpleUserService")
    UserService userService;

    RequestCache requestCache = new HttpSessionRequestCache();

    /**
     * Gets cors headers.
     *
     * @param response the response
     */
    @RequestMapping(method = RequestMethod.OPTIONS)
    public void getCorsHeaders(HttpServletResponse response) {
        response.addHeader("Access-Control-Allow-Origin", "*");
        response.addHeader("Access-Control-Allow-Methods", "GET, OPTIONS");
        response.addHeader("Access-Control-Allow-Headers", "origin, content-type, accept, x-requested-with");
        response.addHeader("Access-Control-Max-Age", "3600");
    }

    /**
     * Login 2 saved request.
     *
     * @param request    the request
     * @param response   the response
     * @param userDetail the user detail
     * @param result     the result
     * @return the saved request
     * @throws ServletException the servlet exception
     * @throws LoginException   the login exception
     */
    @ApiOperation(
            value = "Login to the system",
            notes = "The method accepts requests to authorize a user in the system"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    code = 200,
                    message = "Request completed successfully"
            ),
            @ApiResponse(
                    code = 400,
                    message = "There were problems during the execution of the request - check the correctness of the data",
                    response = ExceptionController.ExceptionDetail.class
            ),
            @ApiResponse(
                    code = 401,
                    message = "The request was rejected because the user is not logged in"
            )
    })
    @ResponseStatus(HttpStatus.OK)
    @GetMapping(
            value = "/in"
    )
    public SavedRequest login(HttpServletRequest request, HttpServletResponse response, UserDetail userDetail,
                        BindingResult result) throws ServletException, LoginException {

        request.login(userDetail.getName().get(), userDetail.getPassword().get());
        new SecurityContextLoginModule().login();
        SavedRequest savedRequest = requestCache.getRequest(request, response);

        return savedRequest;
    }

    /**
     * Logout page.
     *
     * @param request  the request
     * @param response the response
     */
    @ApiOperation(
            value = "Logout from the system",
            notes = "The method accepts requests to logout a user from the system"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    code = 200,
                    message = "Request completed successfully"
            ),
            @ApiResponse(
                    code = 400,
                    message = "There were problems during the execution of the request - check the correctness of the data",
                    response = ExceptionController.ExceptionDetail.class
            ),
            @ApiResponse(
                    code = 401,
                    message = "The request was rejected because the user is not logged in"
            )
    })
    @ResponseStatus(HttpStatus.OK)
    @GetMapping(
            value="/out"
    )
    public void logout (HttpServletRequest request, HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth != null){
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
    }

    /**
     * Find response entity.
     *
     * @return the response entity
     */
    @ApiOperation(
            value = "Get information about the current user",
            notes = "The method accepts requests to get information about the current user"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    code = 200,
                    message = "Request completed successfully",
                    response = UserDetail.class,
                    responseContainer = "ResponseEntity"
            ),
            @ApiResponse(
                    code = 400,
                    message = "There were problems during the execution of the request - check the correctness of the data",
                    response = ExceptionController.ExceptionDetail.class
            ),
            @ApiResponse(
                    code = 401,
                    message = "The request was rejected because the user is not logged in"
            )
    })
    @ResponseStatus(HttpStatus.OK)
    @GetMapping(
            path = "/info",
            produces = "application/json"
    )
    public ResponseEntity<UserDetail> retrieve() throws MapperAdapter.MappingException {

        UserDetail userDetail = this.userService.retrieveByName(SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString());

        if (userDetail == null)
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);

        return ResponseEntity.status(HttpStatus.OK).body(userDetail);
    }
}
