import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {ReactiveFormsModule} from '@angular/forms';
import {TranslateModule} from '@ngx-translate/core';
import {NgbModule} from '@ng-bootstrap/ng-bootstrap';

import {TotpRoutingModule} from './totp-routing.module';
import {TotpComponent} from './totp.component';

@NgModule({
  imports: [
    CommonModule,
    ReactiveFormsModule,
    TranslateModule,
    NgbModule,
    TotpRoutingModule
  ],
  declarations: [
    TotpComponent
  ]
})
export class TotpModule {
}
