import {Component, OnInit} from '@angular/core';
import {Router} from '@angular/router';

import {AuthenticationService} from '../../authentication/authentication.service';
import {AuthenticationTokenService} from "../../authentication/authentication-token.service";

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.scss']
})
export class HeaderComponent implements OnInit {

  menuHidden = true;

  constructor(private router: Router) {
  }

  ngOnInit() {
  }

  toggleMenu() {
    this.menuHidden = !this.menuHidden;
  }

  logout() {
    AuthenticationService.logout()
      .subscribe(() => this.router.navigate(['/login'], {replaceUrl: true}));
  }

  get username(): string | null {
    const credentials = AuthenticationTokenService.authenticatedCredentials;
    return credentials ? credentials.username : null;
  }
}
