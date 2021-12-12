import axios from "axios"

export default async function handler(req, res) {

  const url = "http://localhost:4567/mc"

  const config = {
    method: 'post',
    url,
    data: JSON.stringify(req.body),
    responseType: 'json'
    }

  const response = await axios(config)
  
  res.send(response.status == 200)
}