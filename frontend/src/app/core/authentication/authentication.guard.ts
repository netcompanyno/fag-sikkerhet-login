import {Injectable} from '@angular/core';
import {CanActivate, Router} from '@angular/router';

import {Logger} from '../logger.service';
import {AuthenticationTokenService} from "./authentication-token.service";

const log = new Logger('AuthenticationGuard');

@Injectable()
export class AuthenticationGuard implements CanActivate {

  constructor(private router: Router,
              private authenticationService: AuthenticationTokenService) {
  }

  canActivate(): boolean {
    if (this.authenticationService.isAuthenticated()) {
      return true;
    }

    log.debug('Not authenticated, redirecting...');
    this.router.navigate(['/login'], {replaceUrl: true});
    return false;
  }

}
