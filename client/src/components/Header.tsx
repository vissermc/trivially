import {JSX} from 'react'
import companyLogo from '../assets/penta.png'

function Header(): JSX.Element {
  return (
    <div className="header">
      <img src={companyLogo} alt="Company Logo" className="logo" />
      <h1>Some fun before you go!</h1>
    </div>
  )
}

export default Header