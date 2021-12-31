// import App from 'next/app'
import { Layout } from "../components/layout"
import "../styles/global.css"
import "../styles/command_form.css" //Command Form CSS
import "../styles/footer.css" //Footer CSS
import "../styles/login.css" //Login CSS
import "../styles/media_querys.css" //Media Querys CSS
import "../node_modules/normalize.css/normalize.css" //Normalize CSS

function MyApp({ Component, pageProps }) {
    return (<Layout>
        <Component {...pageProps} />
    </Layout>)
}

// Only uncomment this method if you have blocking data requirements for
// every single page in your application. This disables the ability to
// perform automatic static optimization, causing every page in your app to
// be server-side rendered.
//
// MyApp.getInitialProps = async (appContext) => {
//   // calls page's `getInitialProps` and fills `appProps.pageProps`
//   const appProps = await App.getInitialProps(appContext);
//
//   return { ...appProps }
// }

export default MyApp