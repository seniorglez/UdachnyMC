import axios from "axios"

export default async function handler(req, res) {

  if (!req.method === 'POST') {
    res.status(405).send('Method Not Allowed')

  }

  const url = "http://localhost:4567/mc"

  const config = {
    method: 'post',
    url,
    data: JSON.stringify(req.body),
    responseType: 'json'
  }

  const response = await axios(config)

  res.status(response.status).send('Command executed') //I should return just the code
}