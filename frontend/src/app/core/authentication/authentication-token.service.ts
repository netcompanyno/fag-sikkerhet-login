import {Injectable} from "@angular/core";

export interface Credentials {
  // Customize received credentials here
  username: string;
  token: string;
}

@Injectable()
export class AuthenticationTokenService {
  private _credentials: Credentials | null;

  get credentials(): Credentials | null {
    return this._credentials;
  }

  setCredentials(credentials: Credentials) {
    this._credentials = credentials;
  }

  /**
   * Checks is the user is authenticated.
   * @return {boolean} True if the user is authenticated.
   */
  isAuthenticated(): boolean {
    return !!this.credentials;
  }
}
