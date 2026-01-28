'use client';

import { motion } from 'framer-motion';
import { ProjectCard } from '@/components/ProjectCard';
import { Github, Star, GitBranch } from 'lucide-react';
import { useLanguage } from '@/components/LanguageProvider';

export default function ProjectsClient({ repos }: { repos: any[] }) {
  const { t } = useLanguage();

  return (
    <div className="container mx-auto px-4 py-12">
      <div className="max-w-7xl mx-auto">
        <motion.div
          initial={{ opacity: 0, y: 20 }}
          animate={{ opacity: 1, y: 0 }}
          className="mb-12"
        >
          <div className="flex items-center gap-4 mb-6">
            <div className="p-3 card">
              <Github className="w-6 h-6 text-accent" />
            </div>
            <div>
              <h1 className="text-5xl md:text-6xl font-bold font-mono mb-2">
                <span className="text-foreground">{t('projects.title')}</span>
                <span className="block gradient-text">{t('projects.highlight')}</span>
              </h1>
              <p className="text-lg text-muted-foreground">{t('projects.description')}</p>
            </div>
          </div>

          <div className="flex items-center gap-6 mt-8">
            <div className="flex items-center gap-2 px-3 py-1.5 card">
              <Star className="w-3 h-3 text-accent" />
              <span className="text-sm font-medium">Open Source</span>
            </div>
            <div className="flex items-center gap-2 px-3 py-1.5 card">
              <GitBranch className="w-3 h-3 text-purple-500" />
              <span className="text-sm font-medium">Active Development</span>
            </div>
          </div>
        </motion.div>

        {repos.length === 0 ? (
          <motion.div
            initial={{ opacity: 0 }}
            animate={{ opacity: 1 }}
            className="p-12 rounded-2xl card text-center"
          >
            <p className="text-lg font-mono mb-2">{t('projects.noProjects')}</p>
          </motion.div>
        ) : (
          <motion.div
            initial={{ opacity: 0 }}
            animate={{ opacity: 1 }}
            className="grid md:grid-cols-2 lg:grid-cols-3 gap-6"
          >
            {repos.map((repo, index) => (
              <motion.div
                key={repo.id}
                initial={{ opacity: 0, y: 20 }}
                animate={{ opacity: 1, y: 0 }}
                transition={{ delay: index * 0.1 }}
              >
                <ProjectCard project={repo} />
              </motion.div>
            ))}
          </motion.div>
        )}
      </div>
    </div>
  );
}
