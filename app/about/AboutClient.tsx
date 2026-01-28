'use client';

import { Code2, Server, Database, Cpu, MapPin, GraduationCap, Target, User } from 'lucide-react';
import { motion } from 'framer-motion';

type Dict = {
  title: string;
  highlight: string;
  bio: string;
  journey: string;
  philosophy: string;
  location: string;
  education: string;
  focus: string;
  skills: string;
};

export default function AboutClient({ dict }: { dict: Dict }) {
  const infoItems = [
    { icon: MapPin, text: dict.location },
    { icon: GraduationCap, text: dict.education },
    { icon: Target, text: dict.focus },
  ];

  return (
    <div className="container mx-auto px-4 py-12 relative">
      <div className="max-w-6xl mx-auto">
        <motion.div initial={{ opacity: 0, y: 20 }} animate={{ opacity: 1, y: 0 }}>
          <h1 className="text-6xl font-bold">
            <span>{dict.title}</span>
            <span className="gradient-text">{dict.highlight}</span>
          </h1>

          <p>{dict.bio}</p>
          <p>{dict.journey}</p>
          <p>{dict.philosophy}</p>
        </motion.div>
      </div>
    </div>
  );
}
