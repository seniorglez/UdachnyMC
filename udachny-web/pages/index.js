import Head from "next/head"
import { Canvas } from "@react-three/fiber"
import { Environment } from "@react-three/drei"
import { Suspense } from "react"
import Model from "../components/Model"

export default function Home() {
  return (
    <div className="container">
      <Head>
        <title>UdachnyMC</title>
        <link rel="icon" href="/favicon.ico" />
      </Head>

      <main>
        <h1 className="title">UdachnyMC</h1>

        <p className="description">A simple Minecraft Server manager</p>
        <div className="main-canvas-wrapper">
          <Canvas>
            <Suspense fallback={null}>
              <Model position={[0, 0, 0]} rotation={[0.3, 0.75, 0]} />
              <Environment preset="sunset" background={false} />
            </Suspense>
          </Canvas>
        </div>
      </main>

      <style jsx>{`
        .container {
          min-height: 100vh;
          display: flex;
          flex-direction: column;
          justify-content: center;
          align-items: center;
        }

        main {
          flex: 1;
          display: flex;
          flex-direction: column;
          justify-content: center;
          align-items: center;
        }
      `}</style>

      <style jsx global>{`
        html,
        body {
          padding: 0;
          margin: 0;
          font-family: -apple-system, BlinkMacSystemFont, Segoe UI, Roboto,
            Oxygen, Ubuntu, Cantarell, Fira Sans, Droid Sans, Helvetica Neue,
            sans-serif;
        }

        * {
          box-sizing: border-box;
        }
      `}</style>
    </div>
  )
}
