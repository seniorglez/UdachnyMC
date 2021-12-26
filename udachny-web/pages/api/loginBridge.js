import axios from "axios"
import Cookies from "cookies"
// About server-side cookies with Next https://maxschmitt.me/posts/next-js-cookies/

export default async function handler(req, res) {

  if (!req.method === 'POST') {
    res.status(405).send('Method Not Allowed')
  }

  const url = "http://localhost:4567/request_token"

  const config = {
    method: 'post',
    url,
    data: JSON.stringify(req.body),
    responseType: 'json'
    }

  const response = await axios(config)

  const token = response.data.result

  const cookies = new Cookies(req, res)

  const eDate = new Date();
  eDate.setTime(eDate.getTime() + (5 * 24 * 60 * 60 * 1000)) //5 dias

  cookies.set('jwt',token,{
    httpOnly: false, // true by default
    expires: eDate,
    sameSite: true
  })
  
  res.status(response.status).send(response.status == 200)
}