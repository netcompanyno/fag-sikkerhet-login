import {NgModule, Optional, SkipSelf} from '@angular/core';
import {CommonModule} from '@angular/common';
import {RouteReuseStrategy, RouterModule} from '@angular/router';
import {ConnectionBackend, Http, HttpModule, RequestOptions, XHRBackend} from '@angular/http';
import {TranslateModule} from '@ngx-translate/core';
import {NgbModule} from '@ng-bootstrap/ng-bootstrap';

import {ShellComponent} from './shell/shell.component';
import {HeaderComponent} from './shell/header/header.component';
import {RouteReusableStrategy} from './route-reusable-strategy';
import {AuthenticationService} from './authentication/authentication.service';
import {AuthenticationGuard} from './authentication/authentication.guard';
import {AuthenticationTokenService} from "./authentication/authentication-token.service";

@NgModule({
  imports: [
    CommonModule,
    HttpModule,
    TranslateModule,
    NgbModule,
    RouterModule
  ],
  declarations: [
    HeaderComponent,
    ShellComponent
  ],
  providers: [
    AuthenticationService,
    AuthenticationTokenService,
    AuthenticationGuard,
    {
      provide: RouteReuseStrategy,
      useClass: RouteReusableStrategy
    }
  ]
})
export class CoreModule {

  constructor(@Optional() @SkipSelf() parentModule: CoreModule) {
    // Import guard
    if (parentModule) {
      throw new Error(`${parentModule} has already been loaded. Import Core module in the AppModule only.`);
    }
  }

}
