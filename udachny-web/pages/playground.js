import { Canvas } from '@react-three/fiber'
import { Environment, OrbitControls, useFrame } from "@react-three/drei";
import { Suspense } from 'react'
import Model from '../components/Model'

export default function test() {
    return (
        <div className={"canvas-wrapper"}>
            <Canvas>
                <Suspense fallback={null}>
                    <Model position={[0, 0, 0]} rotation={[0.3, 0.75, 0]}/>
                    <OrbitControls />
                    <Environment preset="sunset" background={false} />
                </Suspense>
            </Canvas>
        </div>
            )
}