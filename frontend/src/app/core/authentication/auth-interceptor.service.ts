import {Injectable} from '@angular/core';
import {HttpEvent, HttpHandler, HttpInterceptor, HttpRequest} from '@angular/common/http';
import {Observable} from "rxjs/Observable";
import {AuthenticationTokenService} from "./authentication-token.service";

@Injectable()
export class AuthInterceptor implements HttpInterceptor {
  constructor() {
  }

  intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    if (AuthenticationTokenService.isAuthenticated()) {
      const credentials = AuthenticationTokenService.authenticatedCredentials;
      const authReq = req.clone({headers: req.headers.set('Authorization', credentials.token)});
      return next.handle(authReq);
    }

    return next.handle(req);
  }
}
