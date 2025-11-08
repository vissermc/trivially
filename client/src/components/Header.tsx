import {JSX} from 'react'
import travelActiveLogo from '../assets/travel_active.png'

function Header(): JSX.Element {
  return (
    <div className="header">
      <img src={travelActiveLogo} alt="Travel Active Logo" className="logo" />
      <h1>Some fun before you go!</h1>
    </div>
  )
}

export default Header