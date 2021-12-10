import axios from "axios"


export default async function handler(req, res) {

  const url = "http://localhost:4567/request_token"

  const config = {
    method: 'post',
    url,
    data: JSON.stringify(req.body),
    responseType: 'json'
    }

  const response = await axios(config)

  const token = response.data.result //save this please


  res.send(response.status == 200)
}