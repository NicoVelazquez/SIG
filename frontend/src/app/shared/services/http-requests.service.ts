import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class HttpRequestsService {

  // TODO - setear url para pegarle al back
  url = 'http://localhost:9000';

  constructor(private http: HttpClient) {
  }

  getClientApplications(clientId: any): Promise<any> {
    // TODO - descomentar y borrar
    // return this.http.get<any[]>(`${this.url}/client/${clientId}/applications`).toPromise();

    return Promise.resolve(
      [{name: 'Nicolas', lastname: 'Velazquez', email: 'nico@gmail.com', type: 'Admin'}]
    );
  }
}
