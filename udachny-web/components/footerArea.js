import Link from 'next/link'

export function FooterArea() {
  return (
    <footer className={"footer-area"}>
      <div className={"footer-addr"}>
        <h1 className={"footer-logo"}>udachny</h1>

        <h2>Contact</h2>

        <address>
          <a className={"footer-btn"} href="mailto:d.dominguez.glez1111@gmail.com">Email Me</a>
        </address>
      </div>

      <ul className={"footer-nav"}>
        <li className={"nav-item"}>
          <h2 className={"nav-title"}>Media</h2>

          <ul className={"nav-ul"}>
            <li>
              <a href="https://twitter.com/DGlez1111" target="_blank">Twitter</a>
            </li>

            <li>
              <a href="https://github.com/seniorglez" target="_blank"> GitHub</a>
            </li>

          </ul>
        </li>

        <li className={"nav-item nav-item--extra"}>
          <h2 className={"nav-title"}>Interest Links</h2>

          <ul className={"nav-ul nav-ul--extra"}>
            <li>
              <a href="https://www.minecraft.net/" target="_blank">Minecraft Web</a>
            </li>

            <li>
              <a href="https://minecraft.fandom.com/wiki/Minecraft_Wiki" target="_blank">Minecraft Wiki</a>
            </li>

            <li>
              <a href="https://www.digminecraft.com/game_commands/index.php" target="_blank">Minecraft Commands</a>
            </li>

            <li>
              <a href="https://minecraft.tools/en/" target="_blank">Minecraft Tools</a>
            </li>

            <li>
              <a href="https://www.reddit.com/r/admincraft/" target="_blank">Admincraft Subreddit</a>
            </li>

            <li>
              <a href="https://discord.com/invite/minecraft" target="_blank">Minecraft Discord</a>
            </li>
          </ul>
        </li>

        <li className={"nav-item"}>
          <h2 className={"nav-title"}>Legal</h2>

          <ul className={"nav-ul"}>
            <li>
              <a href="https://github.com/seniorglez/UdachnyMC/blob/master/LICENSE" target="_blank">LICENSE</a>
            </li>
            <li>
            <Link href="/cookiePolicy"><a>Cookie Policy</a></Link>
            </li>
          </ul>
        </li>
      </ul>

      <div className={"legal"}>
        <p>&copy; 2021 Diego Dominguez Gonzalez. GPL3 LICENSE.</p>

        <div className={"legal__links"}>
          <span>Made with <span className="heart">♥</span> remotely from <a href="https://en.wikipedia.org/wiki/Castile_(historical_region)" target="_blank">Castile</a></span>
        </div>
      </div>
    </footer>)
}