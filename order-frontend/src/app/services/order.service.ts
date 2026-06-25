import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class OrderService {

    private apiUrl = 'http://localhost:8081/api/orders';

    constructor(private http: HttpClient) { }

    placeOrder(order: any): Observable<any>{
      const token = localStorage.getItem('token');

      const headers = new HttpHeaders({
        'Authorization': 'Bearer ' + token
      });

      return this.http.post(this.apiUrl, order, {headers});
    }

}
