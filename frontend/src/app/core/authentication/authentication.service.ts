import {Injectable} from '@angular/core';
import {Observable} from 'rxjs/Observable';
import {of} from 'rxjs/observable/of';
import {HttpClient} from "@angular/common/http";
import {finalize, map} from "rxjs/operators";
import {AuthenticationTokenService} from "./authentication-token.service";
import {and} from "@angular/router/src/utils/collection";

export interface Credentials {
  // Customize received credentials here
  username: string;
  token: string;
}

export interface LoginContext {
  username: string;
  password: string;
}

const credentialsKey = 'credentials';

/**
 * Provides a base for authentication workflow.
 * The Credentials interface as well as login/logout methods should be replaced with proper implementation.
 */
@Injectable()
export class AuthenticationService {

  constructor(private http: HttpClient,
              private authenticationTokenService: AuthenticationTokenService) {
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
          this.authenticationTokenService.setCredentials({username: context.username, token: res})
          return res;
        }),
        map(body => ({username: context.username, token: body})),
      );
  }

  /**
   * Logs out the user and clear credentials.
   * @return {Observable<boolean>} True if the user was logged out successfully.
   */
  logout(): Observable<boolean> {
    // Customize credentials invalidation here
    return of(true);
  }
}
