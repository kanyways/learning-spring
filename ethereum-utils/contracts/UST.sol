pragma solidity ^0.4.23;

import "./token/ERC20/StandardToken.sol";

contract UST is StandardToken {
    string public name = "USTCoin";
    string public symbol = "UST";
    uint8 public decimals = 18;
    uint256 public INITIAL_SUPPLY = 1000000000000000000000000000;

    constructor() public {
        totalSupply_ = INITIAL_SUPPLY;
        balances[msg.sender] = INITIAL_SUPPLY;
    }

}