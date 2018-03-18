import {Injectable} from '@angular/core';
import {CanActivate, Router} from '@angular/router';

import {Logger} from '../logger.service';
import {AuthenticationTokenService} from "./authentication-token.service";

const log = new Logger('AuthenticationGuard');

@Injectable()
export class AuthenticationGuard implements CanActivate {

  constructor(private router: Router) {
  }

  canActivate(): boolean {
    if (AuthenticationTokenService.isAuthenticated()) {
      return true;
    }

    if (AuthenticationTokenService.isPartiallyAuthenticated()) {
      if (AuthenticationTokenService.isTotpMissing()) {
        log.debug('Totp missing, redirecting...');
        this.router.navigate(['/totp'], {replaceUrl: true});
      } else {
        log.debug('Error, redirecting...');
        this.router.navigate(['/error'], {replaceUrl: true});
      }
    } else {
      log.debug('Not authenticated, redirecting...');
      this.router.navigate(['/login'], {replaceUrl: true});
    }
    return false;
  }

}
