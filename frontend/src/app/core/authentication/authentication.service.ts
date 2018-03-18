import {Injectable} from '@angular/core';
import {Observable} from 'rxjs/Observable';
import {of} from 'rxjs/observable/of';
import {HttpClient} from "@angular/common/http";
import {map} from "rxjs/operators";
import {AuthenticationTokenService} from "./authentication-token.service";

export interface Credentials {
  // Customize received credentials here
  username: string;
  token: string;
}

export interface LoginContext {
  username: string;
  password: string;
}

export interface SecurityStepContext {
  authenticationToken: string;
  securityStep: string;
  password: string
}

/**
 * Provides a base for authentication workflow.
 * The Credentials interface as well as login/logout methods should be replaced with proper implementation.
 */
@Injectable()
export class AuthenticationService {

  constructor(private http: HttpClient) {
  }

  /**
   * Authenticates the user.
   * @param {LoginContext} context The login parameters.
   * @return {Observable<Credentials>} The user credentials.
   */
  login(context: LoginContext): Observable<Credentials> {
    return this.http.post('/api/login', context, {responseType: 'text'})
      .pipe(
        map(res => {
          AuthenticationTokenService.setCredentials({username: context.username, token: res});
          return res;
        }),
        map(body => ({username: context.username, token: body})),
      );
  }

  /**
   * Authenticates the user via security step.
   * @param {SecurityStepContext} context The security step parameters.
   * @return {Observable<Credentials>} The user credentials.
   */
  loginSecurityStep(context: SecurityStepContext): Observable<Credentials> {
    let username = AuthenticationTokenService.findUsername(context.authenticationToken);
    return this.http.post('/api/login/security-step', context, {responseType: 'text'})
      .pipe(
        map(res => {
          AuthenticationTokenService.setCredentials({username: username, token: res});
          return res;
        }),
        map(body => ({username: username, token: body})),
      );
  }

  /**
   * Logs out the user and clear credentials.
   * @return {Observable<boolean>} True if the user was logged out successfully.
   */
  static logout(): Observable<boolean> {
    AuthenticationTokenService.deleteCredentials();
    return of(true);
  }
}
