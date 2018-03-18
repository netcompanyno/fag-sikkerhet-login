import {Injectable} from "@angular/core";

export interface Credentials {
  // Customize received credentials here
  username: string;
  token: string;
}

const credentialsKey = 'credentials';
const authenticatedCredentialsKey = 'authenticatedCredentials';

@Injectable()
export class AuthenticationTokenService {

  static get authenticatedCredentials(): Credentials | null {
    let item = this.getAuthenticatedCredentialsItem();
    return item && JSON.parse(item);
  }

  static get credentials(): Credentials | null {
    let item = this.getCredentialsItem();
    return item && JSON.parse(item);
  }

  static setCredentials(credentials: Credentials) {
    let stringifiedCredentials = JSON.stringify(credentials);
    sessionStorage.setItem(credentialsKey, stringifiedCredentials);

    if (AuthenticationTokenService.isCredentialsAuthenticated(credentials)) {
      sessionStorage.setItem(authenticatedCredentialsKey, stringifiedCredentials);
    }
  }

  /**
   * Checks is the user is authenticated.
   * @return {boolean} True if the user is authenticated.
   */
  static isAuthenticated(): boolean {
    return !!this.getAuthenticatedCredentialsItem();
  }

  static isPartiallyAuthenticated() {
    return !this.isAuthenticated() && !!this.getCredentialsItem();
  }

  static isTotpMissing(): boolean {
    let claims = this.credentialsToClaims(AuthenticationTokenService.credentials);
    return claims.securitySteps.includes("TOTP") && !claims.securityStepsCompleted.includes("TOTP");
  }

  static deleteCredentials() {
    sessionStorage.removeItem(credentialsKey);
    sessionStorage.removeItem(authenticatedCredentialsKey);
  }

  static findUsername(authenticationToken: string) {
    let claims = this.tokenToClaims(authenticationToken);
    return claims.sub;
  }

  private static isCredentialsAuthenticated(credentials: Credentials): boolean {
    let claims = this.credentialsToClaims(credentials);

    for (let security of claims.securitySteps) {
      if (!claims.securityStepsCompleted.includes(security)) {
        return false;
      }
    }
    return true;
  }

  private static getAuthenticatedCredentialsItem() {
    return sessionStorage.getItem(authenticatedCredentialsKey);
  }

  private static getCredentialsItem() {
    return sessionStorage.getItem(credentialsKey);
  }

  private static credentialsToClaims(credentials: Credentials) {
    return this.tokenToClaims(credentials.token);
  }

  private static tokenToClaims(token: string) {
    let tokenPartsBase64 = token.split(".");
    return JSON.parse(atob(tokenPartsBase64[1]));
  }
}
