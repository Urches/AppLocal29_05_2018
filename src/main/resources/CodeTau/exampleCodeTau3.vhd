library ieee;
library altera_mf;
use ieee.std_logic_1164.all;
use altera_mf.altera_mf_components.all;
use ieee.std_logic_unsigned.all;

entity Check is
port(f0 : in std_logic;
Nx : in std_logic_vector (4 downto 1);
Ntheta : in std_logic_vector (4 downto 1);
ny : out std_logic;
tau : out std_logic 
);
end Check;
architecture behav of Check is
signal cnt : std_logic_vector(4 downto 1);
signal cnt2 : std_logic_vector(4 downto 1);
signal enable : std_logic;
signal a0 : std_logic;
signal tau_in : std_logic;

begin
process(f0)
begin 
if (f0='1' and f0'event)
then cnt <= cnt - 1;
end if;
end process;

process(cnt)
begin
if (cnt = 0)
then enable <= '1';
else enable <= '0';
end if;
end process;

a0 <= tau_in;

process(f0)
begin
if (f0='1' and f0'event) then
if (enable = '1')
then cnt2 <= Ntheta;
else if (a0 = '1')
then cnt2 <= cnt2 - 1;
end if;
end if;
end if;
end process;

process(Ntheta,Nx)
begin
if (Nx < cnt2)
then tau_in <= '1';
else tau_in <= '0';
end if;
end process;
tau <= tau_in;
ny <= tau_in and f0;
end behav;