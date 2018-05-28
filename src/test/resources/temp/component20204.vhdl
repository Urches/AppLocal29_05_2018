entity component20204 is
port(f0 :in std_logic; -- reference frequency
	x  : in std_logic_vector (8 downto 1);
	Ntheta  : in std_logic_vector (8 downto 1);
	fy  : out std_logic );
