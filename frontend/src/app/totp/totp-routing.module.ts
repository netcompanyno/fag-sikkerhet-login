import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';

import {TotpComponent} from './totp.component';

const routes: Routes = [
  {path: 'totp', component: TotpComponent, data: {title: 'Time-based One-time Password Step'}}
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
  providers: []
})
export class TotpRoutingModule {
}
